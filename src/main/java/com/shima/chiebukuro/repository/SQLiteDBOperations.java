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

    private String getDbUrl() {
        String dbPath = System.getProperty("sqlite.db.path", "/app/data/app.db");
        return "jdbc:sqlite:" + dbPath;
    }

    public void insertQuestion(String title, String question) {
        String sql = "INSERT INTO questions(title, content) VALUES(?, ?)";

        try (Connection conn = DriverManager.getConnection(getDbUrl());
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, question);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Question> selectQuestions() {
        String sql = "SELECT id, title, content, created_at, "
                + "CASE WHEN EXISTS (SELECT 1 FROM pragma_table_info('questions') WHERE name='empathy_count') "
                + "THEN empathy_count ELSE 0 END AS empathy_count "
                + "FROM questions";
        List<Question> questions = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(getDbUrl());
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String createdTime = rs.getString("created_at");
                int empathyCount = rs.getInt("empathy_count");
                questions.add(new Question(id, title, content, createdTime, empathyCount));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return questions;
    }

    public Question selectQuestion(String queryid) {
        String sql = "SELECT id, title, content, created_at, "
                + "CASE WHEN EXISTS (SELECT 1 FROM pragma_table_info('questions') WHERE name='empathy_count') "
                + "THEN empathy_count ELSE 0 END AS empathy_count "
                + "FROM questions WHERE id = ?";
        Question questions = null;

        try (Connection conn = DriverManager.getConnection(getDbUrl());
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, queryid);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String createdTime = rs.getString("created_at");
                int empathyCount = rs.getInt("empathy_count");
                questions = new Question(id, title, content, createdTime, empathyCount);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return questions;
    }

    public void insertAnswer(String questionIdStr, String respondent, String answer) {
        int questionId = Integer.parseInt(questionIdStr);
        String sql = "INSERT INTO answers(question_id, responder_name, answer) VALUES(?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(getDbUrl());
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, questionId);
            pstmt.setString(2, respondent);
            pstmt.setString(3, answer);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Answer> selectAnswer(String questionId) {
        String sql = "SELECT id, question_id, responder_name, answer, created_at, "
                + "CASE WHEN EXISTS (SELECT 1 FROM pragma_table_info('answers') WHERE name='empathy_count') "
                + "THEN empathy_count ELSE 0 END AS empathy_count "
                + "FROM answers WHERE question_id = ?";
        List<Answer> answers = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(getDbUrl());
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(questionId));
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String questionIdValue = String.valueOf(rs.getInt("question_id"));
                String respondent = rs.getString("responder_name");
                String answer = rs.getString("answer");
                String createdTime = rs.getString("created_at");
                int empathyCount = rs.getInt("empathy_count");
                answers.add(new Answer(id, questionIdValue, answer, respondent, createdTime, empathyCount));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return answers;
    }
    
    public void incrementEmpathyCount(int answerId) {
        String sql = "UPDATE answers SET empathy_count = empathy_count + 1 WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(getDbUrl());
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, answerId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void incrementQuestionEmpathyCount(int questionId) {
        String sql = "UPDATE questions SET empathy_count = empathy_count + 1 WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(getDbUrl());
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, questionId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
