package com.shima.chiebukuro.service;

import java.util.List;

import com.shima.chiebukuro.model.Question;
import com.shima.chiebukuro.model.QuestionForm;

/**
 * 質問サービスのインターフェース
 */
public interface QuestionService {

    /**
     * すべての質問を取得
     * 
     * @return 質問のリスト
     */
    List<Question> findByQuestions();

    /**
     * 指定されたIDの質問を取得
     * 
     * @param id 質問ID
     * @return 質問オブジェクト
     */
    Question findByQuestionContent(String id);

    /**
     * 質問を登録
     * 
     * @param questionForm 質問フォームデータ
     */
    void insertQuestion(QuestionForm questionForm);
    
    /**
     * 共感数をインクリメント
     * 
     * @param questionId 質問ID
     */
    void incrementEmpathyCount(int questionId);

    /**
     * 共感数が多い順に質問を取得
     * 
     * @param limit 取得件数
     * @return 質問のリスト
     */
    List<Question> findPopularQuestions(int limit);

}
