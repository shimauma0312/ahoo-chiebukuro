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
import com.shima.chiebukuro.model.User;
import java.util.Optional;

/**
 * SQLiteデータベース操作クラス
 */
@Component
public class SQLiteDBOperations {

    private String getDbUrl() {
        String dbPath = System.getProperty("sqlite.db.path", "/app/data/app.db");
        return "jdbc:sqlite:" + dbPath;
    }

    /**
     * ユーザー名からユーザーを検索
     * 
     * @param username ユーザー名
     * @return ユーザー情報
     */
    public Optional<User> findUserByUsername(String username) {
        String sql = "SELECT id, username, password, role FROM users WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(getDbUrl());
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                return Optional.of(user);
            }

        } catch (SQLException e) {
            System.out.println("User search error: " + e.getMessage());
        }
        return Optional.empty();
    }

    /**
     * ユーザーを登録
     * 
     * @param username ユーザー名
     * @param password パスワード
     * @param role     ロール
     */
    public void insertUser(String username, String password, String role) {
        String sql = "INSERT INTO users(username, password, role) VALUES(?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(getDbUrl());
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, role);
            pstmt.executeUpdate();
            System.out.println("User inserted: " + username);

        } catch (SQLException e) {
            System.out.println("User insert error: " + e.getMessage());
        }
    }

    /**
     * 質問を登録
     * 
     * @param title    タイトル
     * @param question 質問内容
     */
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

    /**
     * ユーザーIDを含めて質問を登録
     * 
     * @param title    タイトル
     * @param question 質問内容
     * @param userId   ユーザーID
     */
    public void insertQuestionWithUser(String title, String question, Long userId) {
        String sql = "INSERT INTO questions(title, content, user_id) VALUES(?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(getDbUrl());
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, question);
            if (userId != null) {
                pstmt.setLong(3, userId);
            } else {
                pstmt.setNull(3, java.sql.Types.INTEGER);
            }
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 全質問を取得
     * 
     * @return 質問リスト
     */
    public List<Question> selectQuestions() {
        String sql = "SELECT q.id, q.title, q.content, q.created_at, "
                + "CASE WHEN EXISTS (SELECT 1 FROM pragma_table_info('questions') WHERE name='empathy_count') "
                + "THEN q.empathy_count ELSE 0 END AS empathy_count, "
                + "q.user_id, u.username "
                + "FROM questions q "
                + "LEFT JOIN users u ON q.user_id = u.id";
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
                Long userId = rs.getObject("user_id") != null ? rs.getLong("user_id") : null;
                String username = rs.getString("username");
                questions.add(new Question(id, title, content, createdTime, empathyCount, userId, username));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return questions;
    }

    /**
     * 質問IDで質問を取得
     * 
     * @param queryid 質問ID
     * @return 質問情報
     */
    public Question selectQuestion(String queryid) {
        String sql = "SELECT q.id, q.title, q.content, q.created_at, "
                + "CASE WHEN EXISTS (SELECT 1 FROM pragma_table_info('questions') WHERE name='empathy_count') "
                + "THEN q.empathy_count ELSE 0 END AS empathy_count, "
                + "q.user_id, u.username "
                + "FROM questions q "
                + "LEFT JOIN users u ON q.user_id = u.id "
                + "WHERE q.id = ?";
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
                Long userId = rs.getObject("user_id") != null ? rs.getLong("user_id") : null;
                String username = rs.getString("username");
                questions = new Question(id, title, content, createdTime, empathyCount, userId, username);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return questions;
    }

    /**
     * 回答を登録
     * 
     * @param questionIdStr 質問ID
     * @param respondent    回答者名
     * @param answer        回答内容
     */
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

    /**
     * ユーザーIDを含めて回答を登録
     * 
     * @param questionIdStr 質問ID
     * @param respondent    回答者名
     * @param answer        回答内容
     * @param userId        ユーザーID
     */
    public void insertAnswerWithUser(String questionIdStr, String respondent, String answer, Long userId) {
        int questionId = Integer.parseInt(questionIdStr);
        String sql = "INSERT INTO answers(question_id, responder_name, answer, user_id) VALUES(?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(getDbUrl());
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, questionId);
            pstmt.setString(2, respondent);
            pstmt.setString(3, answer);
            if (userId != null) {
                pstmt.setLong(4, userId);
            } else {
                pstmt.setNull(4, java.sql.Types.INTEGER);
            }
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 質問IDに紐づく回答を取得
     * 
     * @param questionId 質問ID
     * @return 回答リスト
     */
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

    /**
     * 回答の共感数をインクリメント
     * 
     * @param answerId 回答ID
     */
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

    /**
     * 質問の共感数をインクリメント
     * 
     * @param questionId 質問ID
     */
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

    /**
     * 共感数が多い順に質問を取得
     * 
     * @param limit 取得件数
     * @return 質問のリスト
     */
    public List<Question> selectPopularQuestions(int limit) {
        String sql = "SELECT q.id, q.title, q.content, q.created_at, " +
                "CASE WHEN EXISTS (SELECT 1 FROM pragma_table_info('questions') WHERE name='empathy_count') " +
                "THEN q.empathy_count ELSE 0 END AS empathy_count, " +
                "q.user_id, u.username " +
                "FROM questions q " +
                "LEFT JOIN users u ON q.user_id = u.id " +
                "ORDER BY q.empathy_count DESC LIMIT ?";
        List<Question> questions = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(getDbUrl());
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, limit);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String createdTime = rs.getString("created_at");
                int empathyCount = rs.getInt("empathy_count");
                Long userId = rs.getObject("user_id") != null ? rs.getLong("user_id") : null;
                String username = rs.getString("username");
                questions.add(new Question(id, title, content, createdTime, empathyCount, userId, username));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return questions;
    }
}
