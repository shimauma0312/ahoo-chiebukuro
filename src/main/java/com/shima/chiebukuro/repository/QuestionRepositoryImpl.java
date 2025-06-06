package com.shima.chiebukuro.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.shima.chiebukuro.model.Question;
import com.shima.chiebukuro.model.QuestionForm;

@Repository
public class QuestionRepositoryImpl implements QuestionRepository {

    private final SQLiteDBOperations sqLiteDBOperations;

    public QuestionRepositoryImpl(SQLiteDBOperations sqLiteDBOperations) {
        this.sqLiteDBOperations = sqLiteDBOperations;
    }

    public void insertQuestion(QuestionForm questionForm) {
        sqLiteDBOperations.insertQuestion(questionForm.getTitle(), questionForm.getQuestion());
    }

    public Question findByQuestionContent(String id) {
        Question questions = sqLiteDBOperations.selectQuestion(id);
        return questions;
    }

    @Override
    public List<Question> findAll() {
        List<Question> questions = new ArrayList<>();
        questions = sqLiteDBOperations.selectQuestions();
        return questions;
    }
    
    @Override
    public void incrementEmpathyCount(int questionId) {
        sqLiteDBOperations.incrementQuestionEmpathyCount(questionId);
    }
}
