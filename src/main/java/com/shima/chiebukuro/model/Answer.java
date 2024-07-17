package com.shima.chiebukuro.model;

public class Answer {
    private String questionId;
    private String answer;
    private String name;
    private String date;

    public Answer(String questionId, String answer, String name, String date) {
        this.questionId = questionId;
        this.answer = answer;
        this.name = name;
        this.date = date;
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
