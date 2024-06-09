package com.shima.chiebukuro.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shima.chiebukuro.QuestionForm;
import com.shima.chiebukuro.model.Question;
import com.shima.chiebukuro.repository.QuestionRepository;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    // ShainRepositoryのDI
    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;

    }

    @Override
    public List<Question> findByQuestions() {
        // リポジトリから社員を選択
        return questionRepository.findAll();
    }

    @Override
    public void insertQuestion(QuestionForm questionForm) {
        questionRepository.insertQuestion(questionForm);

    }
}
