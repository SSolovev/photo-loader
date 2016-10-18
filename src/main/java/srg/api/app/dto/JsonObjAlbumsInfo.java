package srg.api.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonObjAlbumsInfo {
    private String updated;
    private String title;
    private String author;
    private String id;

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "JsonObjAlbumsInfo{" +
                "updated='" + updated + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
