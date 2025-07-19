package com.shima.ahoochiebukuro.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.shima.chiebukuro.exception.QuestionNotFoundException;
import com.shima.chiebukuro.exception.ValidationException;
import com.shima.chiebukuro.model.Question;
import com.shima.chiebukuro.model.QuestionForm;
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

    @Test
    public void testFindByQuestionContent_ValidId() {
        // 正常なケースのテスト
        Question question = new Question(1, "タイトル", "コンテンツ", "時間", 0);
        when(questionRepository.findByQuestionContent("1")).thenReturn(question);

        Question result = questionService.findByQuestionContent("1");

        assertNotNull(result);
        assertEquals(question, result);
    }

    @Test
    public void testFindByQuestionContent_NullId() {
        // null IDの場合のテスト
        assertThrows(ValidationException.class, () -> {
            questionService.findByQuestionContent(null);
        });
    }

    @Test
    public void testFindByQuestionContent_EmptyId() {
        // 空IDの場合のテスト
        assertThrows(ValidationException.class, () -> {
            questionService.findByQuestionContent("");
        });
    }

    @Test
    public void testFindByQuestionContent_QuestionNotFound() {
        // 質問が見つからない場合のテスト
        when(questionRepository.findByQuestionContent("999")).thenReturn(null);

        assertThrows(QuestionNotFoundException.class, () -> {
            questionService.findByQuestionContent("999");
        });
    }

    @Test
    public void testInsertQuestion_ValidForm() {
        // 正常なケースのテスト
        QuestionForm questionForm = new QuestionForm();
        questionForm.setTitle("Valid Title");
        questionForm.setQuestion("Valid question content");

        // メソッドが正常に実行されることを確認
        assertDoesNotThrow(() -> {
            questionService.insertQuestion(questionForm);
        });

        // リポジトリが呼び出されることを確認
        verify(questionRepository, times(1)).insertQuestion(questionForm);
    }

    @Test
    public void testInsertQuestion_NullForm() {
        // null formの場合のテスト
        assertThrows(ValidationException.class, () -> {
            questionService.insertQuestion(null);
        });
    }

    @Test
    public void testInsertQuestion_InappropriateTitle() {
        // 不適切なタイトルの場合のテスト
        QuestionForm questionForm = new QuestionForm();
        questionForm.setTitle("This is spam content");
        questionForm.setQuestion("Valid question");

        assertThrows(ValidationException.class, () -> {
            questionService.insertQuestion(questionForm);
        });
    }

    @Test
    public void testIncrementEmpathyCount_ValidId() {
        // 正常なケースのテスト
        Question question = new Question(1, "Title", "Content", "Time", 5);
        when(questionRepository.findByQuestionContent("1")).thenReturn(question);

        assertDoesNotThrow(() -> {
            questionService.incrementEmpathyCount(1);
        });

        verify(questionRepository, times(1)).incrementEmpathyCount(1);
    }

    @Test
    public void testIncrementEmpathyCount_InvalidId() {
        // 無効なIDの場合のテスト
        assertThrows(ValidationException.class, () -> {
            questionService.incrementEmpathyCount(0);
        });

        assertThrows(ValidationException.class, () -> {
            questionService.incrementEmpathyCount(-1);
        });
    }

    @Test
    public void testIncrementEmpathyCount_QuestionNotFound() {
        // 質問が見つからない場合のテスト
        when(questionRepository.findByQuestionContent("999")).thenReturn(null);

        assertThrows(QuestionNotFoundException.class, () -> {
            questionService.incrementEmpathyCount(999);
        });
    }
}