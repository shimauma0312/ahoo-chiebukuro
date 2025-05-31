package com.shima.chiebukuro.model;

public class Answer {
    private int id;
    private String questionId;
    private String answer;
    private String responderName;
    private String date;
    private int empathyCount;

    public Answer(int id, String questionId, String answer, String name, String date, int empathyCount) {
        this.id = id;
        this.questionId = questionId;
        this.answer = answer;
        this.responderName = name;
        this.date = date;
        this.empathyCount = empathyCount;
    }

    public int getId() {
        return id;
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
    
    public int getEmpathyCount() {
        return empathyCount;
    }
}
