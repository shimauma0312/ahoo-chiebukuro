package com.shima.chiebukuro.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.shima.chiebukuro.model.Answer;
import com.shima.chiebukuro.model.AnswerForm;

@Repository
public class AnswerRepositoryImpl implements AnswerRepository {
    private final SQLiteDBOperations sqLiteDBOperations;

    public AnswerRepositoryImpl(SQLiteDBOperations sqLiteDBOperations) {
        this.sqLiteDBOperations = sqLiteDBOperations;
    }

    public void insertAnswer(AnswerForm answerForm) {
        sqLiteDBOperations.insertAnswer(answerForm.getQuestionId(), answerForm.getResponderName(), answerForm.getAnswer());
    }

    public List<Answer> findByAnswerContent(String id) {
        List<Answer> answers = sqLiteDBOperations.selectAnswer(id);
        return answers;
    }
}
