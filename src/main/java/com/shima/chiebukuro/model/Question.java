package com.shima.chiebukuro.model;

public class Question {
    private String title;
    private String content;
    private String createdTime;

    public Question(String title, String content, String createdTime) {
        this.title = title;
        this.content = content;
        this.createdTime = createdTime;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

}
