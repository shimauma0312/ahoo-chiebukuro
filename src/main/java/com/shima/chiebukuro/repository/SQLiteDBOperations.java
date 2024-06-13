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
}
