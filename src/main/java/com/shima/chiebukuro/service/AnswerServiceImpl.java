package com.shima.chiebukuro.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shima.chiebukuro.model.Answer;
import com.shima.chiebukuro.model.AnswerForm;
import com.shima.chiebukuro.repository.AnswerRepository;

@Service
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;

    public AnswerServiceImpl(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;

    }

    @Override
    public List<Answer> findByAnswerContent(String id) {
        List<Answer> ansList = answerRepository.findByAnswerContent(id);
        return ansList;
    }

    @Override
    public void insertAnswer(AnswerForm answerForm) {
        answerRepository.insertAnswer(answerForm);
    }

}
