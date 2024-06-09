package com.shima.chiebukuro.repository;

import java.util.List;

import com.shima.chiebukuro.model.Question;

public interface QuestionRepository {

    List<Question> findAll();

}
