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
import srg.api.app.exceptions.CreateAlbumException;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;


@RestController
public class UploaderController {


    @Autowired
    private UploaderService uploaderService;

    @RequestMapping("/")
    public String getAlbumsUrl() {
        return uploaderService.getAlbumsUrl();
    }

    @RequestMapping("/getAlbums")
    public String getAlbumsInfo() {
        return uploaderService.getAlbumsInfo();
    }


    @RequestMapping("/create")
    public String addNewAlbum() throws CreateAlbumException {
        JsonObjAlbum obj = uploaderService.createAlbum("RestAlbum");
        return obj.toString();
    }

    @RequestMapping("/uploadPhotos")
    public String uploadPhotos() throws CreateAlbumException, IOException {
        uploaderService.uploadPhotosFromDirCountTime(new File("d:\\Foto\\forTest\\"));
        return "Ok";
    }

    @RequestMapping("/uploadPic")
    public String uploadPic() throws CreateAlbumException, IOException {
        uploaderService.uploadPhoto("http://api-fotki.yandex.ru/api/users/sery-volk/album/170613/photos/", new File("d:\\Foto\\forTest\\DSCN1935.JPG"));
        return "Ok";
    }
}
