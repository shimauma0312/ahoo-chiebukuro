package com.shima.chiebukuro.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shima.chiebukuro.exception.AnswerNotFoundException;
import com.shima.chiebukuro.exception.QuestionNotFoundException;
import com.shima.chiebukuro.exception.ValidationException;
import com.shima.chiebukuro.model.Answer;
import com.shima.chiebukuro.model.AnswerForm;
import com.shima.chiebukuro.model.Question;
import com.shima.chiebukuro.repository.AnswerRepository;
import com.shima.chiebukuro.repository.QuestionRepository;

/**
 * 回答サービス実装クラス
 */
@Service
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    public AnswerServiceImpl(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    /**
     * 質問IDに紐づく回答を取得
     * 
     * @param questionId 質問ID
     * @return 回答リスト
     */
    @Override
    public List<Answer> findByAnswerContent(String questionId) {
        if (questionId == null || questionId.trim().isEmpty()) {
            throw new ValidationException("Question ID cannot be null or empty");
        }

        Question question = questionRepository.findByQuestionContent(questionId);
        if (question == null) {
            throw new QuestionNotFoundException("Question with ID " + questionId + " not found");
        }
        
        List<Answer> answers = answerRepository.findByAnswerContent(questionId);
        return answers;
    }

    /**
     * 回答を登録
     * 
     * @param answerForm 回答フォーム
     */
    @Override
    public void insertAnswer(AnswerForm answerForm) {
        if (answerForm == null) {
            throw new ValidationException("Answer form cannot be null");
        }

        if (answerForm.getQuestionId() == null || answerForm.getQuestionId().trim().isEmpty()) {
            throw new ValidationException("Question ID is required for answer submission");
        }

        Question question = questionRepository.findByQuestionContent(answerForm.getQuestionId());
        if (question == null) {
            throw new QuestionNotFoundException("Cannot submit answer: Question with ID " + answerForm.getQuestionId() + " not found");
        }

        if (answerForm.getAnswer() != null) {
            String trimmedAnswer = answerForm.getAnswer().trim();
            if (trimmedAnswer.isEmpty()) {
                throw new ValidationException("Answer content cannot be empty or contain only whitespace");
            }
            if (trimmedAnswer.toLowerCase().contains("spam") || trimmedAnswer.toLowerCase().contains("inappropriate")) {
                throw new ValidationException("Answer contains inappropriate content");
            }
        }

        if (answerForm.getResponderName() != null && answerForm.getResponderName().trim().isEmpty()) {
            throw new ValidationException("Responder name cannot be empty if provided");
        }
        
        answerRepository.insertAnswer(answerForm);
    }
    
    /**
     * 回答の共感数をインクリメント
     * 
     * @param answerId 回答ID
     */
    @Override
    public void incrementEmpathyCount(int answerId) {
        if (answerId <= 0) {
            throw new ValidationException("Answer ID must be a positive integer");
        }

        answerRepository.incrementEmpathyCount(answerId);
    }
}
