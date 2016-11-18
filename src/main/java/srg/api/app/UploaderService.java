package srg.api.app;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import srg.api.app.dto.JsonObjAlbum;
import srg.api.app.dto.JsonObjAlbumsInfo;
import srg.api.app.dto.JsonObjPhoto;
import srg.api.app.dto.JsonObjService;
import srg.api.app.exceptions.CreateAlbumException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Sergey on 19.10.2016.
 */
@Service
public class UploaderService {
    private static final String DEFAULT_MIME = "application/json;type=entry";

    private Logger log = LoggerFactory.getLogger(UploaderController.class);

    @Value("${token}")
    private String token;
    @Value("${initial.url}")
    private String url;

    @Autowired
    private RestTemplate restTemplate;

    private ExecutorService executorService;

    private String albumUrl;

    public UploaderService() {
        LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(100);
        executorService = new ThreadPoolExecutor(5, 5,
                0L, TimeUnit.MILLISECONDS, queue);
//        this.albumUrl = getAlbumsUrl();
    }


    public String getAlbumsUrl() {
        if (this.albumUrl == null) {
            HttpEntity<String> entity = new HttpEntity<>(getHeaders(DEFAULT_MIME, null));

            ResponseEntity<JsonObjService> resp = restTemplate.exchange(url, HttpMethod.GET, entity, JsonObjService.class);

            System.out.println(resp.getBody());
            this.albumUrl = resp.getBody().getCollections().getAlbumList().getHref();
        }

        return this.albumUrl;
    }


    public String getAlbumsInfo() {

        HttpEntity entity = new HttpEntity(getHeaders(DEFAULT_MIME, null));

        String albumUrl = getAlbumsUrl();

        ResponseEntity<JsonObjAlbumsInfo> resp = restTemplate.exchange(albumUrl, HttpMethod.GET, entity, JsonObjAlbumsInfo.class);
        return resp.getBody().toString();
    }


    public JsonObjAlbum createAlbum(String albumTitle) throws CreateAlbumException {
        JsonObjAlbum newAlbum = new JsonObjAlbum();
        newAlbum.setTitle(albumTitle);
        newAlbum.setSummary("The best summary");
        HttpEntity<JsonObjAlbum> entity = new HttpEntity<>(newAlbum, getHeaders(DEFAULT_MIME, null));

        String albumUrl = getAlbumsUrl();

        ResponseEntity<JsonObjAlbum> resp = restTemplate.exchange(albumUrl, HttpMethod.POST, entity, JsonObjAlbum.class);
        if (HttpStatus.CREATED.equals(resp.getStatusCode())) {
            return resp.getBody();
        } else {
            throw new CreateAlbumException("Exception while creating new web album. Http code: " + resp.getStatusCode());
        }
    }

    public void uploadPhotosFromDirCountTime(File file) throws IOException {
        long start = System.currentTimeMillis();
        System.out.println("START");
        uploadPhotosFromDir(file);
        boolean termination = false;
        try {
            executorService.shutdown();
            termination = executorService.awaitTermination(5, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Terminated: " + termination);
        System.out.println(System.currentTimeMillis() - start);
        System.out.println("END");
    }

    public void uploadPhotosFromDir(File file) throws IOException {

        try {
            JsonObjAlbum album = createAlbum(file.getName());

            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (f.isDirectory()) {
                        uploadPhotosFromDir(f);
                    } else {
                        executorService.execute(() -> {
                            try {
                                System.out.println(uploadPhoto(album.getLinks().get("photos"), f));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });

                    }
                }
            }
//        } catch (ResponseParsingException e) {
//            System.out.println("We have troubles with this album: " + file.getName());
//            e.printStackTrace();
        } catch (CreateAlbumException e) {
            e.printStackTrace();
        }
    }

    public JsonObjPhoto uploadPhoto(String url, File file) throws IOException {
        ResponseEntity<JsonObjPhoto> result;
        try (InputStream in = new FileInputStream(file.getPath())) {

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.IMAGE_JPEG);
            headers.setContentLength(file.length());
            headers.set("Authorization", token);


            HttpEntity<byte[]> requestEntity = new HttpEntity<>(IOUtils.toByteArray(in), headers);
            result = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
                    JsonObjPhoto.class);
        }


//        MultiValueMap<String, Object> multipartRequest = new LinkedMultiValueMap<>();
//
//// creating an HttpEntity for the JSON part
//        HttpHeaders jsonHeader = new HttpHeaders();
//        jsonHeader.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        jsonHeader.setContentType(MediaType.MULTIPART_FORM_DATA);
//        HttpEntity<JsonObject> jsonHttpEntity = new HttpEntity<>(jsonObject, jsonHeader);
//
//// creating an HttpEntity for the binary part
//        HttpHeaders pictureHeader = new HttpHeaders();
//        pictureHeader.setContentType(MediaType.IMAGE_PNG);
//        HttpEntity<ByteArrayResource> picturePart = new HttpEntity<>(pngPicture, pictureHeader);
//
//// putting the two parts in one request
//        multipartRequest.add("myAwesomeJsonData", jsonPart);
//        multipartRequest.add("file", picturePart);
//
//        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(multipartRequest, header);
//        ResultObject result = restTemplate.postForObject(UPLOAD_URL, requestEnti
//
//
//        //===========================================
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//        headers.setContentLength(file.length());
//        headers.set("Authorization", token);
//
//        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
//        map.add("file", new FileSystemResource(file.getPath()));
//
//        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(
//                map, headers);
//        ResponseEntity<JsonObjPhoto> result = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
//                JsonObjPhoto.class);
//=======================================
//        JsonObjPhoto newPhoto = new JsonObjPhoto();
//        newPhoto.setTitle(file.getName());
//        newPhoto.setSummary("The best summary");
//        HttpEntity<JsonObjPhoto> entity = new HttpEntity<>(newPhoto, getHeaders("image/jpeg", file.length()));
//
//        ResponseEntity<JsonObjPhoto> resp = restTemplate.exchange(url, HttpMethod.POST, entity, JsonObjPhoto.class);

        return result.getBody();

    }

    //TODO: rewrite to builder
    private HttpHeaders getHeaders(String contentType, Long length) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("Content-Type", contentType);
        if (length != null) {
            headers.setContentLength(length);
        }
        headers.set("Authorization", token);
        return headers;
    }
}
