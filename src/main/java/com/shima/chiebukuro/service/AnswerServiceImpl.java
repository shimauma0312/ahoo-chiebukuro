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

@Service
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    public AnswerServiceImpl(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public List<Answer> findByAnswerContent(String questionId) {
        if (questionId == null || questionId.trim().isEmpty()) {
            throw new ValidationException("Question ID cannot be null or empty");
        }
        
        // Verify that the question exists
        Question question = questionRepository.findByQuestionContent(questionId);
        if (question == null) {
            throw new QuestionNotFoundException("Question with ID " + questionId + " not found");
        }
        
        List<Answer> answers = answerRepository.findByAnswerContent(questionId);
        return answers;
    }

    @Override
    public void insertAnswer(AnswerForm answerForm) {
        if (answerForm == null) {
            throw new ValidationException("Answer form cannot be null");
        }
        
        // Validate question ID
        if (answerForm.getQuestionId() == null || answerForm.getQuestionId().trim().isEmpty()) {
            throw new ValidationException("Question ID is required for answer submission");
        }
        
        // Verify that the question exists
        Question question = questionRepository.findByQuestionContent(answerForm.getQuestionId());
        if (question == null) {
            throw new QuestionNotFoundException("Cannot submit answer: Question with ID " + answerForm.getQuestionId() + " not found");
        }
        
        // Additional business validation
        if (answerForm.getAnswer() != null) {
            String trimmedAnswer = answerForm.getAnswer().trim();
            if (trimmedAnswer.isEmpty()) {
                throw new ValidationException("Answer content cannot be empty or contain only whitespace");
            }
            // Check for inappropriate content
            if (trimmedAnswer.toLowerCase().contains("spam") || trimmedAnswer.toLowerCase().contains("inappropriate")) {
                throw new ValidationException("Answer contains inappropriate content");
            }
        }
        
        // Validate responder name (business rule: cannot be empty if provided)
        if (answerForm.getResponderName() != null && answerForm.getResponderName().trim().isEmpty()) {
            throw new ValidationException("Responder name cannot be empty if provided");
        }
        
        answerRepository.insertAnswer(answerForm);
    }
    
    @Override
    public void incrementEmpathyCount(int answerId) {
        if (answerId <= 0) {
            throw new ValidationException("Answer ID must be a positive integer");
        }
        
        // For this method, we need to check if the answer exists
        // Since we don't have a direct findById method in AnswerRepository,
        // we'll add validation logic that can be extended later
        
        answerRepository.incrementEmpathyCount(answerId);
    }
}
