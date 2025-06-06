package com.shima.chiebukuro.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteDBInit {
    public static void setup() {
        // SQLite connection string - 環境変数かプロパティから取得するとより柔軟になります
        String dbPath = System.getProperty("sqlite.db.path", "/app/data/app.db");
        String url = "jdbc:sqlite:" + dbPath;

        // SQL statements for creating tables
        String createQuestionsTable = "CREATE TABLE IF NOT EXISTS questions (\n"
                + "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "    title TEXT NOT NULL,\n"
                + "    content TEXT NOT NULL,\n"
                + "    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n"
                + "    empathy_count INTEGER DEFAULT 0\n"
                + ");";

        String createAnswersTable = "CREATE TABLE IF NOT EXISTS answers (\n"
                + "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "    question_id INTEGER NOT NULL,\n"
                + "    answer TEXT NOT NULL,\n"
                + "    responder_name TEXT,\n"
                + "    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n"
                + "    empathy_count INTEGER DEFAULT 0,\n"
                + "    FOREIGN KEY (question_id) REFERENCES questions (id)\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {

            // Create questions table
            stmt.execute(createQuestionsTable);

            // Create answers table
            stmt.execute(createAnswersTable);

            // カラムが存在するかチェックして、なければ追加する
            addColumnIfNotExists(conn, "questions", "empathy_count", "INTEGER DEFAULT 0");
            addColumnIfNotExists(conn, "answers", "empathy_count", "INTEGER DEFAULT 0");

            System.out.println("Tables created successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    // テーブルにカラムを追加するヘルパーメソッド
    private static void addColumnIfNotExists(Connection conn, String tableName, String columnName, String columnType) throws SQLException {
        boolean columnExists = false;
        
        // カラムが存在するかチェック
        try (ResultSet rs = conn.getMetaData().getColumns(null, null, tableName, columnName)) {
            columnExists = rs.next();
        }
        
        // カラムが存在しなければ追加
        if (!columnExists) {
            try (Statement stmt = conn.createStatement()) {
                String sql = String.format("ALTER TABLE %s ADD COLUMN %s %s", tableName, columnName, columnType);
                stmt.execute(sql);
                System.out.println("Added column " + columnName + " to table " + tableName);
            }
        }
    }
}
