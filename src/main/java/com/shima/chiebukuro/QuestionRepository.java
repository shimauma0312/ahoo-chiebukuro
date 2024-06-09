package com.shima.chiebukuro;

import java.util.List;

public interface QuestionRepository {

    List<Question> findAll();

}
