package com.shima.chiebukuro.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.shima.chiebukuro.model.Answer;
import com.shima.chiebukuro.model.AnswerForm;

/**
 * 回答リポジトリ実装クラス
 */
@Repository
public class AnswerRepositoryImpl implements AnswerRepository {
    private final SQLiteDBOperations sqLiteDBOperations;

    public AnswerRepositoryImpl(SQLiteDBOperations sqLiteDBOperations) {
        this.sqLiteDBOperations = sqLiteDBOperations;
    }

    /**
     * 回答を登録
     * 
     * @param answerForm 回答フォーム
     */
    public void insertAnswer(AnswerForm answerForm) {
        if (answerForm.getUserId() != null) {
            sqLiteDBOperations.insertAnswerWithUser(answerForm.getQuestionId(), answerForm.getResponderName(),
                    answerForm.getAnswer(), answerForm.getUserId());
        } else {
            sqLiteDBOperations.insertAnswer(answerForm.getQuestionId(), answerForm.getResponderName(),
                    answerForm.getAnswer());
        }
    }

    /**
     * 質問IDに紐づく回答を取得
     * 
     * @param id 質問ID
     * @return 回答リスト
     */
    public List<Answer> findByAnswerContent(String id) {
        List<Answer> answers = sqLiteDBOperations.selectAnswer(id);
        return answers;
    }
    
    /**
     * 回答の共感数をインクリメント
     * 
     * @param answerId 回答ID
     */
    @Override
    public void incrementEmpathyCount(int answerId) {
        sqLiteDBOperations.incrementEmpathyCount(answerId);
    }
}
