package com.example.jiemian.bean;

/**
 * @author admin
 */
public class VideoData {
    private String name;
    private int url;

    public VideoData(String name, int url) {
        this.name = name;
        this.url = url;
    }

    public VideoData() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUrl() {
        return url;
    }

    public void setUrl(int url) {
        this.url = url;
    }
}
