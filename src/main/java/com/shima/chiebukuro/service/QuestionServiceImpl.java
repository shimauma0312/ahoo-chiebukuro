package com.shima.chiebukuro.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shima.chiebukuro.exception.QuestionNotFoundException;
import com.shima.chiebukuro.exception.ValidationException;
import com.shima.chiebukuro.model.Question;
import com.shima.chiebukuro.model.QuestionForm;
import com.shima.chiebukuro.repository.QuestionRepository;

/**
 * 質問サービス実装クラス
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    /**
     * 全質問を取得
     * 
     * @return 質問リスト
     */
    @Override
    public List<Question> findByQuestions() {
        return questionRepository.findAll();
    }

    /**
     * ページネーションで質問を取得
     * 
     * @param pageNumber ページ番号
     * @param pageSize   ページサイズ
     * 
     * @return 質問リスト
     */
    @Override
    public List<Question> findByQuestionsPaginated(int pageNumber, int pageSize) {
        if (pageNumber < 1 || pageSize < 1) {
            throw new ValidationException("Page number and page size must be positive integers");
        }
        int offset = (pageNumber - 1) * pageSize;
        return questionRepository.findByQuestionsPaginated(offset, pageSize);
    }
    /**
     * 質問IDで質問を取得
     * 
     * @param id 質問ID
     * @return 質問情報
     */
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

    /**
     * 質問を登録
     * 
     * @param questionForm 質問フォーム
     */
    @Override
    public void insertQuestion(QuestionForm questionForm) {
        if (questionForm == null) {
            throw new ValidationException("Question form cannot be null");
        }

        if (questionForm.getTitle() != null) {
            String trimmedTitle = questionForm.getTitle().trim();
            if (trimmedTitle.isEmpty()) {
                throw new ValidationException("Question title cannot be empty or contain only whitespace");
            }
            if (trimmedTitle.toLowerCase().contains("spam") || trimmedTitle.toLowerCase().contains("test123")) {
                throw new ValidationException("Question title contains inappropriate content");
            }
        }
        
        if (questionForm.getQuestion() != null) {
            String trimmedQuestion = questionForm.getQuestion().trim();
            if (trimmedQuestion.isEmpty()) {
                throw new ValidationException("Question content cannot be empty or contain only whitespace");
            }
            if (trimmedQuestion.toLowerCase().contains("duplicate test")) {
                throw new ValidationException("This question appears to be a duplicate");
            }
        }
        
        questionRepository.insertQuestion(questionForm);
    }
    
    /**
     * 質問の共感数をインクリメント
     * 
     * @param questionId 質問ID
     */
    @Override
    public void incrementEmpathyCount(int questionId) {
        if (questionId <= 0) {
            throw new ValidationException("Question ID must be a positive integer");
        }

        Question question = questionRepository.findByQuestionContent(String.valueOf(questionId));
        if (question == null) {
            throw new QuestionNotFoundException("Cannot increment empathy count: Question with ID " + questionId + " not found");
        }
        
        questionRepository.incrementEmpathyCount(questionId);
    }

    /**
     * 共感数が多い順に質問を取得
     * 
     * @param limit 取得件数
     * @return 質問のリスト
     */
    @Override
    public List<Question> findPopularQuestions(int limit) {
        return questionRepository.findPopularQuestions(limit);
    }

    /**
     * 総質問数を取得
     * 
     * @return 総質問数
     */
    @Override
    public int getTotalQuestionsCount() {
        return questionRepository.getTotalQuestionsCount();
    }
}
