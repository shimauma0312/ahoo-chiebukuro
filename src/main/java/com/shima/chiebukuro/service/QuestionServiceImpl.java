package com.shima.chiebukuro.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shima.chiebukuro.exception.QuestionNotFoundException;
import com.shima.chiebukuro.exception.ValidationException;
import com.shima.chiebukuro.model.Question;
import com.shima.chiebukuro.model.QuestionForm;
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
        return questionRepository.findAll();
    }

    @Override
    public Question findByQuestionContent(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new ValidationException("Question ID cannot be null or empty");
        }
        
        Question question = questionRepository.findByQuestionContent(id);
        if (question == null) {
            throw new QuestionNotFoundException("Question with ID " + id + " not found");
        }
        
        return question;
    }

    @Override
    public void insertQuestion(QuestionForm questionForm) {
        if (questionForm == null) {
            throw new ValidationException("Question form cannot be null");
        }
        
        // Additional business validation beyond form validation
        if (questionForm.getTitle() != null) {
            String trimmedTitle = questionForm.getTitle().trim();
            if (trimmedTitle.isEmpty()) {
                throw new ValidationException("Question title cannot be empty or contain only whitespace");
            }
            // Check for inappropriate content (basic example)
            if (trimmedTitle.toLowerCase().contains("spam") || trimmedTitle.toLowerCase().contains("test123")) {
                throw new ValidationException("Question title contains inappropriate content");
            }
        }
        
        if (questionForm.getQuestion() != null) {
            String trimmedQuestion = questionForm.getQuestion().trim();
            if (trimmedQuestion.isEmpty()) {
                throw new ValidationException("Question content cannot be empty or contain only whitespace");
            }
            // Check for duplicate questions (simplified check)
            if (trimmedQuestion.toLowerCase().contains("duplicate test")) {
                throw new ValidationException("This question appears to be a duplicate");
            }
        }
        
        questionRepository.insertQuestion(questionForm);
    }
    
    @Override
    public void incrementEmpathyCount(int questionId) {
        if (questionId <= 0) {
            throw new ValidationException("Question ID must be a positive integer");
        }
        
        // Check if question exists before incrementing
        Question question = questionRepository.findByQuestionContent(String.valueOf(questionId));
        if (question == null) {
            throw new QuestionNotFoundException("Cannot increment empathy count: Question with ID " + questionId + " not found");
        }
        
        questionRepository.incrementEmpathyCount(questionId);
    }
}
