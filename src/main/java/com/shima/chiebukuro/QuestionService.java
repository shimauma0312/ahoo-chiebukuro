package com.shima.chiebukuro;

import java.util.List;

public interface QuestionService {

    List<Question> findByQuestions();

    void insert(QuestionForm questionForm);

}
