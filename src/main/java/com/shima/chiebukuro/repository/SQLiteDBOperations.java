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

    public void insert(String title, String question) {
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
        String sql = "SELECT title, content FROM questions";
        List<Question> questions = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:app.db");
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String title = rs.getString("title");
                String content = rs.getString("content");
                questions.add(new Question(title, content));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return questions;
    }

    public List<String> selectTitles() {
        String sql = "SELECT title FROM questions";
        List<String> titles = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:app.db");
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String title = rs.getString("title");
                titles.add(title);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return titles;
    }
}
