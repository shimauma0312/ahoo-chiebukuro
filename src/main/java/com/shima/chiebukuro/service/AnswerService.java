package com.shima.chiebukuro.service;

import java.util.List;

import com.shima.chiebukuro.model.Answer;
import com.shima.chiebukuro.model.AnswerForm;

/**
 * 回答サービスインターフェース
 */
public interface AnswerService {

    /**
     * 質問IDに紐づく回答を取得
     * 
     * @param id 質問ID
     * @return 回答リスト
     */
    List<Answer> findByAnswerContent(String id);

    /**
     * 回答を登録
     * 
     * @param answerForm 回答フォーム
     */
    void insertAnswer(AnswerForm answerForm);
    
    /**
     * 回答の共感数をインクリメント
     * 
     * @param answerId 回答ID
     */
    void incrementEmpathyCount(int answerId);

}
