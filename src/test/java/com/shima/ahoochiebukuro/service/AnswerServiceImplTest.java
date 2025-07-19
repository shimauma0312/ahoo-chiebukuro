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
import com.shima.chiebukuro.model.Answer;
import com.shima.chiebukuro.model.AnswerForm;
import com.shima.chiebukuro.model.Question;
import com.shima.chiebukuro.repository.AnswerRepository;
import com.shima.chiebukuro.repository.QuestionRepository;
import com.shima.chiebukuro.service.AnswerServiceImpl;

import java.util.Collections;
import java.util.List;

public class AnswerServiceImplTest {

    @Mock
    private AnswerRepository answerRepository;
    
    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private AnswerServiceImpl answerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByAnswerContent_ValidQuestionId() {
        // 正常なケースのテスト
        String questionId = "1";
        Question question = new Question(1, "Title", "Content", "Time", 0);
        Answer answer = new Answer(1, questionId, "Answer content", "Responder", "Date", 0);
        List<Answer> answers = Collections.singletonList(answer);

        when(questionRepository.findByQuestionContent(questionId)).thenReturn(question);
        when(answerRepository.findByAnswerContent(questionId)).thenReturn(answers);

        List<Answer> result = answerService.findByAnswerContent(questionId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(answer, result.get(0));
    }

    @Test
    public void testFindByAnswerContent_NullQuestionId() {
        // null question IDの場合のテスト
        assertThrows(ValidationException.class, () -> {
            answerService.findByAnswerContent(null);
        });
    }

    @Test
    public void testFindByAnswerContent_EmptyQuestionId() {
        // 空question IDの場合のテスト
        assertThrows(ValidationException.class, () -> {
            answerService.findByAnswerContent("");
        });
    }

    @Test
    public void testFindByAnswerContent_QuestionNotFound() {
        // 質問が見つからない場合のテスト
        String questionId = "999";
        when(questionRepository.findByQuestionContent(questionId)).thenReturn(null);

        assertThrows(QuestionNotFoundException.class, () -> {
            answerService.findByAnswerContent(questionId);
        });
    }

    @Test
    public void testInsertAnswer_ValidForm() {
        // 正常なケースのテスト
        AnswerForm answerForm = new AnswerForm();
        answerForm.setQuestionId("1");
        answerForm.setAnswer("Valid answer content");
        answerForm.setResponderName("Valid Name");

        Question question = new Question(1, "Title", "Content", "Time", 0);
        when(questionRepository.findByQuestionContent("1")).thenReturn(question);

        assertDoesNotThrow(() -> {
            answerService.insertAnswer(answerForm);
        });

        verify(answerRepository, times(1)).insertAnswer(answerForm);
    }

    @Test
    public void testInsertAnswer_NullForm() {
        // null formの場合のテスト
        assertThrows(ValidationException.class, () -> {
            answerService.insertAnswer(null);
        });
    }

    @Test
    public void testInsertAnswer_NullQuestionId() {
        // null question IDの場合のテスト
        AnswerForm answerForm = new AnswerForm();
        answerForm.setQuestionId(null);
        answerForm.setAnswer("Valid answer");

        assertThrows(ValidationException.class, () -> {
            answerService.insertAnswer(answerForm);
        });
    }

    @Test
    public void testInsertAnswer_QuestionNotFound() {
        // 質問が見つからない場合のテスト
        AnswerForm answerForm = new AnswerForm();
        answerForm.setQuestionId("999");
        answerForm.setAnswer("Valid answer");

        when(questionRepository.findByQuestionContent("999")).thenReturn(null);

        assertThrows(QuestionNotFoundException.class, () -> {
            answerService.insertAnswer(answerForm);
        });
    }

    @Test
    public void testInsertAnswer_InappropriateContent() {
        // 不適切な内容の場合のテスト
        AnswerForm answerForm = new AnswerForm();
        answerForm.setQuestionId("1");
        answerForm.setAnswer("This is spam content");

        Question question = new Question(1, "Title", "Content", "Time", 0);
        when(questionRepository.findByQuestionContent("1")).thenReturn(question);

        assertThrows(ValidationException.class, () -> {
            answerService.insertAnswer(answerForm);
        });
    }

    @Test
    public void testIncrementEmpathyCount_ValidId() {
        // 正常なケースのテスト
        assertDoesNotThrow(() -> {
            answerService.incrementEmpathyCount(1);
        });

        verify(answerRepository, times(1)).incrementEmpathyCount(1);
    }

    @Test
    public void testIncrementEmpathyCount_InvalidId() {
        // 無効なIDの場合のテスト
        assertThrows(ValidationException.class, () -> {
            answerService.incrementEmpathyCount(0);
        });

        assertThrows(ValidationException.class, () -> {
            answerService.incrementEmpathyCount(-1);
        });
    }
}