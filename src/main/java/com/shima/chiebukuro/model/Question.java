package com.shima.chiebukuro.model;

public class Question {
    private int id;
    private String title;
    private String content;
    private String createdTime;

    public Question(int id, String title, String content, String createdTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdTime = createdTime;
    }

    public int getId() {
        return id;
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

    public void setId(int id) {
        this.id = id;
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
