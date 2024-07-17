package com.shima.chiebukuro.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.shima.chiebukuro.model.Answer;
import com.shima.chiebukuro.model.Question;

@Component
public class SQLiteDBOperations {

    public void insertQuestion(String title, String question) {
        String sql = "INSERT INTO questions(title, content) VALUES(?, ?)";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:app.db");
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, question);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Question> selectQuestions() {
        String sql = "SELECT id, title, content, created_at FROM questions";
        List<Question> questions = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:app.db");
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String createdTime = rs.getString("created_at");
                questions.add(new Question(id, title, content, createdTime));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return questions;
    }

    public Question selectQuestion(String queryid) {
        String sql = "SELECT id, title, content, created_at FROM questions WHERE id = ?";
        Question questions = null;

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:app.db");
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, queryid);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String createdTime = rs.getString("created_at");
                questions = new Question(id, title, content, createdTime);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return questions;
    }

    public void insertAnswer(String questionIdStr, String respondent, String answer) {
        int questionId = Integer.parseInt(questionIdStr);
        String sql = "INSERT INTO answers(question_id, responder_name, answer) VALUES(?, ?, ?)";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:app.db");
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, questionId);
            pstmt.setString(2, answer);
            pstmt.setString(3, respondent);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Answer> selectAnswer(String questionId) {
        String sql = "SELECT question_id, responder_name, answer, created_at FROM answers WHERE question_id = ?";
        List<Answer> answers = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:app.db");
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(questionId));
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String questionIdValue = String.valueOf(rs.getInt("question_id"));
                String respondent = rs.getString("responder_name");
                String answer = rs.getString("answer");
                String createdTime = rs.getString("created_at");
                answers.add(new Answer(questionIdValue, answer, respondent, createdTime));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return answers;
    }
}
