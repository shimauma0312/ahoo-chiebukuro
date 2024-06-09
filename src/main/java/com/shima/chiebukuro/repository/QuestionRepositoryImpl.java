package com.shima.chiebukuro.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.shima.chiebukuro.QuestionForm;
import com.shima.chiebukuro.model.Question;
import com.shima.chiebukuro.model.SQLiteDBOperations;

@Repository
public class QuestionRepositoryImpl implements QuestionRepository {

    private final SQLiteDBOperations sqLiteDBOperations;

    public QuestionRepositoryImpl(SQLiteDBOperations sqLiteDBOperations) {
        this.sqLiteDBOperations = sqLiteDBOperations;
    }

    public void insertQuestion(QuestionForm questionForm) {
        sqLiteDBOperations.insert(questionForm.getTitle(), questionForm.getQuestion());
    }

    @Override
    public List<Question> findAll() {
        List<Question> questions = new ArrayList<>();
        questions = sqLiteDBOperations.selectQuestions();
        return questions;
    }
}
