package com.movetto.dtos;

public class ImageDto {
    private String url;

    public ImageDto() {
        //Empty for Framework
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ImageDto{" +
                "url='" + url + '\'' +
                '}';
    }
}
