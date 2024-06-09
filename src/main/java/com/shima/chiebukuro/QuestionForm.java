package com.shima.chiebukuro;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class QuestionForm {

    @NotEmpty(message = "題名を入力してください")
    @Pattern(regexp = ".{1,18}", message = "題名は18文字以内で入力してください")
    private String title;

    @NotEmpty(message = "質問内容を入力してください")
    @Pattern(regexp = ".{1,255}", message = "質問内容は255文字以内で入力してください")
    private String question;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
