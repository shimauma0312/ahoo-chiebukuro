package com.shima.chiebukuro;

public class Question {
    private String title;
    private String content;

    public Question(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
