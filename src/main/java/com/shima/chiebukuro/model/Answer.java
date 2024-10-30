package com.shima.chiebukuro.model;

public class Answer {
    private String questionId;
    private String answer;
    private String responderName;
    private String date;

    public Answer(String questionId, String answer, String name, String date) {
        this.questionId = questionId;
        this.answer = answer;
        this.responderName = name;
        this.date = date;
    }

    public String getQuestionId() {
        return questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public String getName() {
        return responderName;
    }

    public String getDate() {
        return date;
    }

}
