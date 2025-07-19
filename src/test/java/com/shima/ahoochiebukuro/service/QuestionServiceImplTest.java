package com.shima.ahoochiebukuro.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.shima.chiebukuro.model.Question;
import com.shima.chiebukuro.repository.QuestionRepository;
import com.shima.chiebukuro.service.QuestionServiceImpl;

import java.util.Collections;
import java.util.List;

public class QuestionServiceImplTest {

    @Mock
    private QuestionRepository questionRepository; // QuestionRepositoryのモックオブジェクト

    @InjectMocks
    private QuestionServiceImpl questionService; // モックオブジェクトを注入したQuestionServiceImplのインスタンス

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // 各テストメソッドの前にモックオブジェクトを初期化
    }

    @Test
    public void testFindByQuestions() {
        // テスト用のQuestionオブジェクトを作成
        Question question = new Question(1, "タイトル", "コンテンツ", "んー時間", 0);

        // questionRepository.findAll()が呼ばれたときに、作成したQuestionオブジェクトを返すように設定
        when(questionRepository.findAll()).thenReturn(Collections.singletonList(question));

        // questionService.findByQuestions()を呼び出し、その結果を取得
        List<Question> questions = questionService.findByQuestions();

        // 結果がnullでないことを確認
        assertNotNull(questions);

        // 結果のリストのサイズが1であることを確認
        assertEquals(1, questions.size());

        // 結果のリストの最初の要素が期待通りのQuestionオブジェクトであることを確認
        assertEquals(question, questions.get(0));
    }
}