package srg.api.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonObjCollections {

    @JsonProperty("photo-list")
    private JsonObjElem photoList;
    @JsonProperty("album-list")
    private JsonObjElem albumList;
    @JsonProperty("tag-list")
    private JsonObjElem tagList;

    public JsonObjElem getPhotoList() {
        return photoList;
    }

    public void setPhotoList(JsonObjElem photoList) {
        this.photoList = photoList;
    }

    public JsonObjElem getAlbumList() {
        return albumList;
    }

    public void setAlbumList(JsonObjElem albumList) {
        this.albumList = albumList;
    }

    public JsonObjElem getTagList() {
        return tagList;
    }

    public void setTagList(JsonObjElem tagList) {
        this.tagList = tagList;
    }

    @Override
    public String toString() {
        return "JsonObjCollections{" +
                "photoList=" + photoList +
                ", albumList=" + albumList +
                ", tagList=" + tagList +
                '}';
    }
}
