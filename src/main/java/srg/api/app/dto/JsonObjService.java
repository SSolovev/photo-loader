package srg.api.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonObjService {
    private String title;
    private JsonObjCollections collections;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public JsonObjCollections getCollections() {
        return collections;
    }

    public void setCollections(JsonObjCollections collections) {
        this.collections = collections;
    }
}
