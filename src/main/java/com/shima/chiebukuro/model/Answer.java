package com.shima.chiebukuro.model;

public class Answer {
    private String id;
    private String questionId;
    private String answer;
    private String name;
    private String date;

    public Answer(String id, String questionId, String answer, String name, String date) {
        this.id = id;
        this.questionId = questionId;
        this.answer = answer;
        this.name = name;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getQuestionId() {
        return questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

}
