package com.shima.chiebukuro.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class AnswerForm {

    private String name;

    @NotEmpty(message = "回答を入力してください")
    @Pattern(regexp = ".{1,255}", message = "回答内容は255文字以内で入力してください")
    private String answer;

    private int questionId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

}
