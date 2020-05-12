package com.movetto.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewsDto {

    private String id;
    private String title;
    private List<String> labels;
    private List<ImageDto> images;
    private String content;
    private Date updated;

    public NewsDto(){
        //Empty for Framework
    }

    public NewsDto(String id, String title, List<String> labels) {
        this.id = id;
        this.title = title;
        this.labels = labels;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<ImageDto> getImages() {
        return images;
    }

    public void setImages(List<ImageDto> images) {
        this.images = images;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Date getUpdated() {
        return updated;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "NewsDto{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", labels=" + labels +
                ", images=" + images +
                ", content='" + content + '\'' +
                ", updated=" + updated +
                '}';
    }
}
