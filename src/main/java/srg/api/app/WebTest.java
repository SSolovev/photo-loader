package srg.api.app;

import com.sun.syndication.feed.atom.Content;
import com.sun.syndication.feed.atom.Entry;
import com.sun.syndication.feed.atom.Feed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.propono.utils.ProponoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.sun.syndication.propono.atom.client.AtomClientFactory;
import com.sun.syndication.propono.atom.client.ClientAtomService;
import com.sun.syndication.propono.atom.client.NoAuthStrategy;
import com.sun.syndication.propono.atom.common.AtomService;
import com.sun.syndication.propono.atom.common.Collection;
import com.sun.syndication.propono.atom.common.Workspace;
import srg.api.app.dto.JsonObjAlbum;
import srg.api.app.dto.JsonObjAlbumsInfo;
import srg.api.app.dto.JsonObjService;

import java.util.Arrays;
import java.util.List;


@RestController
public class WebTest {
    //    @RequestMapping("/")
    @Value("${token}")
    private String token;
    @Autowired
    private RestTemplate restTemplate;
    private static final String SERVICE_LINK = "http://api-fotki.yandex.ru/api/me/";

    @RequestMapping("/")
    public String getAlbumsUrl()  {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON,MediaType.APPLICATION_JSON_UTF8));
        headers.set("Authorization", "OAuth AQAAAAAAk6qAAAOGYaAYGmFz8kMJnykRuucKAGY");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<JsonObjService> resp = restTemplate.exchange(SERVICE_LINK, HttpMethod.GET, entity, JsonObjService.class);

        System.out.println(resp.getBody());
        return resp.getBody().getCollections().getAlbumList().getHref();


    }

    @RequestMapping("/getAlbums")
    public String getAlbumsInfo()  {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("DataServiceVersion", "1.0");
        headers.set("Authorization", "OAuth AQAAAAAAk6qAAAOGYaAYGmFz8kMJnykRuucKAGY");

        HttpEntity<Entry> entity = new HttpEntity<>(headers);

        String albumUrl = getAlbumsUrl();

        ResponseEntity<JsonObjAlbumsInfo> resp = restTemplate.exchange(albumUrl, HttpMethod.GET, entity, JsonObjAlbumsInfo.class);
        return resp.getBody().toString();
    }


    @RequestMapping("/create")
    public String createAlbum()  {
        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("Content-Type", "application/json;type=entry");

//        Accept: application/json
        headers.set("Authorization", token);
        JsonObjAlbum newAlbum = new JsonObjAlbum();
        newAlbum.setTitle("RestAlbum");
        newAlbum.setSummary("The  best summary");
//        Content c = new Content();
//        c.setValue("Summary for new RestAlbum val");
//        c.setSrc("Summary for new RestAlbum src");
//        newAlbum.setSummary(c);
        HttpEntity<JsonObjAlbum> entity = new HttpEntity<>(newAlbum, headers);
//        List<Collection> collections = tst().getCollections();
        String albumUrl = getAlbumsUrl();
//        ResponseEntity<Entry> resp = restTemplate.postForEntity(albumUrl, entity, Entry.class);
//        ResponseEntity<JsonObjAlbum> resp = restTemplate.postForEntity(albumUrl, entity, JsonObjAlbum.class);

        JsonObjAlbum resp = restTemplate.postForObject(albumUrl, entity, JsonObjAlbum.class);
//        ResponseEntity<JsonObjAlbum> resp = restTemplate.exchange(albumUrl, HttpMethod.POST, entity, JsonObjAlbum.class);

//        restTemplate.getForEntity(albumUrl, entity, Entry.class);
//        Entry resp =  restTemplate.postForObject(albumUrl,  entity, Entry.class);
        return resp.toString();
    }
}
