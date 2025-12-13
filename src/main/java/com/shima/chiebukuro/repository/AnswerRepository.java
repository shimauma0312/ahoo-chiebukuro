package com.shima.chiebukuro.repository;

import java.util.List;

import com.shima.chiebukuro.model.Answer;
import com.shima.chiebukuro.model.AnswerForm;

/**
 * 回答リポジトリインターフェース
 */
public interface AnswerRepository {
    /**
     * 回答を登録
     * 
     * @param answerForm 回答フォーム
     */
    void insertAnswer(AnswerForm answerForm);

    /**
     * 質問IDに紐づく回答を取得
     * 
     * @param id 質問ID
     * @return 回答リスト
     */
    List<Answer> findByAnswerContent(String id);
    
    /**
     * 回答の共感数をインクリメント
     * 
     * @param answerId 回答ID
     */
    void incrementEmpathyCount(int answerId);
}
