package com.shima.chiebukuro.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class AnswerForm {

    private String responderName;

    @NotEmpty(message = "回答を入力してください")
    @Pattern(regexp = ".{1,255}", message = "回答内容は255文字以内で入力してください")
    private String answer;

    private String questionId;

    public String getResponderName() {
        return responderName;
    }

    public void setResponderName(String respondent) {
        this.responderName = respondent;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

}
