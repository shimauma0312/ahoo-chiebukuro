package com.shima.ahoochiebukuro.repository;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.shima.chiebukuro.repository.QuestionRepositoryImpl;
import com.shima.chiebukuro.repository.SQLiteDBOperations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.shima.chiebukuro.model.Question;
import com.shima.chiebukuro.model.QuestionForm;

import java.util.List;
import java.util.Collections;

public class QuestionRepositoryImplTest {

    @Mock
    private SQLiteDBOperations sqLiteDBOperations; // SQLiteDBOperationsのモックオブジェクト

    @InjectMocks
    private QuestionRepositoryImpl questionRepository; // モックオブジェクトを注入したQuestionRepositoryImplのインスタンス

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // 各テストメソッドの前にモックオブジェクトを初期化
    }

    @Test
    public void testInsertQuestion() {
        // テスト用のQuestionFormオブジェクトを作成し、セッターメソッドで値を設定
        QuestionForm questionForm = new QuestionForm();
        questionForm.setTitle("タイトル");
        questionForm.setQuestion("質問内容");
        // insertQuestionメソッドを呼び出し
        questionRepository.insertQuestion(questionForm);

        // insertQuestionが正しく呼び出されたことを検証
        verify(sqLiteDBOperations, times(1)).insertQuestion(questionForm.getTitle(), questionForm.getQuestion());
    }

    @Test
    public void testFindByQuestionContent() {
        // テスト用のQuestionオブジェクトを作成
        Question question = new Question(1, "タイトル", "質問内容", "何か時間");

        // findByQuestionContentが呼ばれたときに、作成したQuestionオブジェクトを返すように設定
        when(sqLiteDBOperations.selectQuestion("1")).thenReturn(question);

        // findByQuestionContentメソッドを呼び出し、その結果を取得
        Question result = questionRepository.findByQuestionContent("1");

        // 結果が期待通りであることを確認
        assertEquals(question, result);
    }

    @Test
    public void testFindAll() {
        // テスト用のQuestionオブジェクトのリストを作成
        Question question = new Question(1, "タイトル", "質問内容", "なんか時間");
        List<Question> questionList = Collections.singletonList(question);

        // findAllが呼ばれたときに、作成したリストを返すように設定
        when(sqLiteDBOperations.selectQuestions()).thenReturn(questionList);

        // findAllメソッドを呼び出し、その結果を取得
        List<Question> result = questionRepository.findAll();

        // 結果が期待通りであることを確認
        assertEquals(questionList, result);
    }
}