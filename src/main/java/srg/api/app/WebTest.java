package srg.api.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import srg.api.app.dto.JsonObjAlbum;
import srg.api.app.dto.JsonObjAlbumsInfo;
import srg.api.app.dto.JsonObjService;

import java.util.Arrays;


@RestController
public class WebTest {

    @Value("${token}")
    private String token;
    @Value("${initial.url}")
    private String url;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/")
    public String getAlbumsUrl() {

        HttpEntity<String> entity = new HttpEntity<>(getHeaders());

        ResponseEntity<JsonObjService> resp = restTemplate.exchange(url, HttpMethod.GET, entity, JsonObjService.class);

        System.out.println(resp.getBody());
        return resp.getBody().getCollections().getAlbumList().getHref();
    }

    @RequestMapping("/getAlbums")
    public String getAlbumsInfo() {

        HttpEntity entity = new HttpEntity(getHeaders());

        String albumUrl = getAlbumsUrl();

        ResponseEntity<JsonObjAlbumsInfo> resp = restTemplate.exchange(albumUrl, HttpMethod.GET, entity, JsonObjAlbumsInfo.class);
        return resp.getBody().toString();
    }


    @RequestMapping("/create")
    public String createAlbum() {

        JsonObjAlbum newAlbum = new JsonObjAlbum();
        newAlbum.setTitle("RestAlbum");
        newAlbum.setSummary("The best summary");
        HttpEntity<JsonObjAlbum> entity = new HttpEntity<>(newAlbum, getHeaders());

        String albumUrl = getAlbumsUrl();

        ResponseEntity<JsonObjAlbum> resp = restTemplate.exchange(albumUrl, HttpMethod.POST, entity, JsonObjAlbum.class);
        JsonObjAlbum obj = resp.getBody();
        return obj.toString();
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("Content-Type", "application/json;type=entry");
        headers.set("Authorization", token);
        return headers;
    }
}
