package com.example.demo;

import java.util.List;

import org.springframework.stereotype.Service;

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
    public void insert(QuestionForm questionForm) {
    }
}