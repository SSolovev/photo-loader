package srg.api.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import srg.api.app.dto.JsonObjAlbum;
import srg.api.app.exceptions.CreateAlbumException;

import java.io.File;
import java.io.IOException;


@RestController
public class UploaderController {

    private static final String DEFAULT_ALBUM = "Service Album";
    private static final String DEFAULT_ALBUM_URL = "http://api-fotki.yandex.ru/api/users/sery-volk/album/170613/photos/";
    private Logger log = LoggerFactory.getLogger(UploaderController.class);

    @Autowired
    private UploaderService uploaderService;


    @RequestMapping("/")
    public String getAlbumsUrl() {
        log.info("Finding link to all albums");
        return uploaderService.getAlbumsUrl();
    }

    @RequestMapping("/getAlbums")
    public String getAlbumsInfo() {
        log.info("Finding all albums names");
        return uploaderService.getAlbumsInfo();
    }


    @RequestMapping("/create")
    public String addNewAlbum(String album) throws CreateAlbumException {

        if (album == null) {
            album = DEFAULT_ALBUM;
        }
        log.info("Creating new album with name {}", album);
        JsonObjAlbum obj = uploaderService.createAlbum(album);

        return obj.toString();
    }

    @RequestMapping("/uploadPhotos")
    public String uploadPhotos(String path) throws CreateAlbumException, IOException {
        if (path == null) {
            log.warn("Path is empty. Cant upload anything");
            return "Path is empty";
        } else {
            log.info("Uploading photos from path", path);
            uploaderService.uploadPhotosFromDirCountTime(new File(path));
            return "Ok";
        }

    }

    @RequestMapping("/uploadPic")
    public String uploadPic(String album, String path) throws CreateAlbumException, IOException {
//        String defAlbum = "http://api-fotki.yandex.ru/api/users/sery-volk/album/170613/photos/";

        if (path == null) {
            log.warn("Path is empty. Cant upload anything");
            return "Path is empty";
        } else {
            String targetAlbum = album != null ? album : DEFAULT_ALBUM_URL;
            log.info("Upload photos from folder: {} to album: {},targetAlbum", path, targetAlbum);
            uploaderService.uploadPhoto(targetAlbum, new File(path));
            return "Ok";
        }
    }
}
