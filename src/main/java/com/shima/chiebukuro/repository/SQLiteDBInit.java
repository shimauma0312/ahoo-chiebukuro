package com.shima.chiebukuro.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * SQLiteデータベースの初期化クラス
 */
public class SQLiteDBInit {
    /**
     * データベースのセットアップを実行
     * テーブル作成と初期データ投入を行う
     */
    public static void setup() {
        String dbPath = System.getProperty("sqlite.db.path", "/app/data/app.db");
        String url = "jdbc:sqlite:" + dbPath;

        String createQuestionsTable = "CREATE TABLE IF NOT EXISTS questions (\n"
                + "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "    title TEXT NOT NULL,\n"
                + "    content TEXT NOT NULL,\n"
                + "    author TEXT,\n"
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

        String createUsersTable = "CREATE TABLE IF NOT EXISTS users (\n"
                + "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "    username TEXT NOT NULL UNIQUE,\n"
                + "    password TEXT NOT NULL,\n"
                + "    role TEXT DEFAULT 'USER'\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {

            stmt.execute(createUsersTable);
            stmt.execute(createQuestionsTable);
            stmt.execute(createAnswersTable);

            addColumnIfNotExists(conn, "questions", "empathy_count", "INTEGER DEFAULT 0");
            addColumnIfNotExists(conn, "answers", "empathy_count", "INTEGER DEFAULT 0");
            addColumnIfNotExists(conn, "questions", "user_id", "INTEGER");
            addColumnIfNotExists(conn, "answers", "user_id", "INTEGER");
            System.out.println("Tables created successfully.");

            insertInitialData(conn);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * 初期データを投入
     * 
     * @param conn データベース接続
     */
    private static void insertInitialData(Connection conn) {
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as count FROM users");
            if (rs.next() && rs.getInt("count") > 0) {
                System.out.println("Initial data already exists. Skipping...");
                return;
            }

            String hashedPassword = "$2a$10$xTv0fxKj5FZqZQqGVxW5J.5yZXP8fXIqvFPZKKk8nQxQXYBPXYKFy";

            stmt.executeUpdate(
                    "INSERT INTO users (username, password, role) VALUES ('ポチ太郎', '" + hashedPassword + "', 'USER')");
            stmt.executeUpdate(
                    "INSERT INTO users (username, password, role) VALUES ('カレー好き子', '" + hashedPassword + "', 'USER')");
            stmt.executeUpdate(
                    "INSERT INTO users (username, password, role) VALUES ('ネコ派一郎', '" + hashedPassword + "', 'USER')");
            stmt.executeUpdate(
                    "INSERT INTO users (username, password, role) VALUES ('昼寝マスター', '" + hashedPassword + "', 'USER')");
            stmt.executeUpdate(
                    "INSERT INTO users (username, password, role) VALUES ('ラーメン仙人', '" + hashedPassword + "', 'USER')");
            stmt.executeUpdate(
                    "INSERT INTO users (username, password, role) VALUES ('おにぎり職人', '" + hashedPassword + "', 'USER')");
            stmt.executeUpdate(
                    "INSERT INTO users (username, password, role) VALUES ('ゲーム廃人', '" + hashedPassword + "', 'USER')");
            stmt.executeUpdate(
                    "INSERT INTO users (username, password, role) VALUES ('朝弱太郎', '" + hashedPassword + "', 'USER')");
            stmt.executeUpdate(
                    "INSERT INTO users (username, password, role) VALUES ('清潔感ゼロ', '" + hashedPassword + "', 'USER')");
            stmt.executeUpdate(
                    "INSERT INTO users (username, password, role) VALUES ('天然ボケ子', '" + hashedPassword + "', 'USER')");
            stmt.executeUpdate(
                    "INSERT INTO users (username, password, role) VALUES ('常識知らず', '" + hashedPassword + "', 'USER')");
            stmt.executeUpdate(
                    "INSERT INTO users (username, password, role) VALUES ('カオス博士', '" + hashedPassword + "', 'USER')");
            stmt.executeUpdate(
                    "INSERT INTO users (username, password, role) VALUES ('適当マン', '" + hashedPassword + "', 'USER')");
            stmt.executeUpdate(
                    "INSERT INTO users (username, password, role) VALUES ('思考停止', '" + hashedPassword + "', 'USER')");
            stmt.executeUpdate(
                    "INSERT INTO users (username, password, role) VALUES ('迷子のプロ', '" + hashedPassword + "', 'USER')");
            System.out.println("Initial users created.");

            stmt.executeUpdate(
                    "INSERT INTO questions (title, content, user_id, empathy_count) VALUES ('カレーに入れるとうまい具材', '明日カレー作るんですけど、何入れたらうまいですかね？チョコレートとか入れるって聞いたんですけどマジですか？', 2, 15)");
            stmt.executeUpdate(
                    "INSERT INTO questions (title, content, user_id, empathy_count) VALUES ('猫が起こしてくれません', '毎朝5時に起きたいんですけど、猫が起こしてくれません。どうしたらいいですか？目覚まし時計は持ってません。', 3, 23)");
            stmt.executeUpdate(
                    "INSERT INTO questions (title, content, user_id, empathy_count) VALUES ('パソコンの電源の入れ方', 'パソコン買ったんですけど電源ボタンが見つかりません。どこにありますか？叩いたら起動しますか？', 1, 8)");
            stmt.executeUpdate(
                    "INSERT INTO questions (title, content, user_id, empathy_count) VALUES ('冷蔵庫に入れると冷える理由', '冷蔵庫ってなんで物を冷やせるんですか？魔法ですか？冷凍庫はもっと強い魔法ですか？', 4, 31)");
            stmt.executeUpdate(
                    "INSERT INTO questions (title, content, user_id, empathy_count) VALUES ('月曜日が来ない方法', '日曜日の夜なんですけど、月曜日が来ないようにする方法ありますか？時間を止めたいです。', 8, 42)");
            stmt.executeUpdate(
                    "INSERT INTO questions (title, content, user_id, empathy_count) VALUES ('コーヒーとコーラの違い', 'コーヒーとコーラって色が似てるけど何が違うんですか？両方とも茶色いし炭酸入ってるし。', 5, 12)");
            stmt.executeUpdate(
                    "INSERT INTO questions (title, content, user_id, empathy_count) VALUES ('スマホが喋らない', 'スマホに「おはよう」って言っても返事してくれません。壊れてますか？', 1, 7)");
            stmt.executeUpdate(
                    "INSERT INTO questions (title, content, user_id, empathy_count) VALUES ('ラーメンの正しい食べ方', 'ラーメンって麺から食べるんですか？それともスープから飲むんですか？どんぶり投げつけていいですか？', 5, 19)");
            stmt.executeUpdate(
                    "INSERT INTO questions (title, content, user_id, empathy_count) VALUES ('睡眠時間を削る方法', '1日3時間しか寝なくても元気でいられる方法を教えてください。コーヒー100杯飲めばいいですか？', 4, 6)");
            stmt.executeUpdate(
                    "INSERT INTO questions (title, content, user_id, empathy_count) VALUES ('パンダの白黒パターン', 'パンダってなんで白黒なんですか？カラーにできませんか？塗り絵してもいいですか？', 7, 28)");
            stmt.executeUpdate(
                    "INSERT INTO questions (title, content, user_id, empathy_count) VALUES ('WiFiが目に見えない', 'WiFiって目に見えないんですけど、本当に飛んでるんですか？嘘じゃないですか？', 6, 11)");
            stmt.executeUpdate(
                    "INSERT INTO questions (title, content, user_id, empathy_count) VALUES ('お金が増える方法', 'お金を銀行に預けてるんですけど全然増えません。土に埋めたら芽が出て増えますか？', 2, 34)");
            stmt.executeUpdate(
                    "INSERT INTO questions (title, content, user_id, empathy_count) VALUES ('鏡が左右反転する理由', '鏡って左右は反転するのに上下は反転しないのなんでですか？鏡壊れてますか？', 3, 16)");
            stmt.executeUpdate(
                    "INSERT INTO questions (title, content, user_id, empathy_count) VALUES ('体重計が壊れてる', '体重計に乗ったら数字がめっちゃ増えてました。絶対壊れてます。直す方法ありますか？', 4, 25)");
            stmt.executeUpdate(
                    "INSERT INTO questions (title, content, user_id, empathy_count) VALUES ('カビキラーは洗顔料に使えますか？？', 'カビキラーって顔の汚れも落とせそうじゃないですか？洗顔フォーム高いんで代用したいんですけど。', 9, 52)");
            stmt.executeUpdate(
                    "INSERT INTO questions (title, content, user_id, empathy_count) VALUES ('ハイターで歯を白くする方法', '歯が黄色いんですけど、ハイターで磨いたら白くなりますか？漂白剤だし効きそうなんですが。', 9, 47)");
            stmt.executeUpdate(
                    "INSERT INTO questions (title, content, user_id, empathy_count) VALUES ('電子レンジでスマホ充電', 'スマホの充電遅いんで電子レンジに入れたら早く充電できますか？電気だし。', 10, 38)");
            stmt.executeUpdate(
                    "INSERT INTO questions (title, content, user_id, empathy_count) VALUES ('醤油は飲み物ですか？', '醤油って液体だから飲み物ですよね？コーヒーの代わりに飲んでもいいですか？', 11, 29)");
            stmt.executeUpdate(
                    "INSERT INTO questions (title, content, user_id, empathy_count) VALUES ('雨の日に傘の代わりになるもの', '傘忘れたんですけど、段ボール被れば濡れませんか？防水性ありますよね？', 10, 15)");
            stmt.executeUpdate(
                    "INSERT INTO questions (title, content, user_id, empathy_count) VALUES ('洗濯機に自分も入れる？', '洗濯機で服洗うんだったら、自分も一緒に入ったら体も洗えて一石二鳥じゃないですか？', 9, 44)");
            stmt.executeUpdate(
                    "INSERT INTO questions (title, content, user_id, empathy_count) VALUES ('ガムは栄養になりますか', 'ガムずっと噛んでたら痩せますか？あと飲み込んだら栄養になりますか？', 11, 21)");
            stmt.executeUpdate(
                    "INSERT INTO questions (title, content, user_id, empathy_count) VALUES ('エレベーターでジャンプ', 'エレベーター降りる直前にジャンプしたら無重力体験できますか？宇宙飛行士になれますか？', 12, 33)");
            stmt.executeUpdate(
                    "INSERT INTO questions (title, content, user_id, empathy_count) VALUES ('接着剤で傷を治す', 'ケガしたんですけど絆創膏がないので瞬間接着剤で傷口くっつけていいですか？早そうだし。', 13, 56)");
            stmt.executeUpdate(
                    "INSERT INTO questions (title, content, user_id, empathy_count) VALUES ('ドライヤーで料理', 'ドライヤーって熱風出るからフライパンの代わりに料理できますか？肉焼けますか？', 10, 18)");
            stmt.executeUpdate(
                    "INSERT INTO questions (title, content, user_id, empathy_count) VALUES ('車のエンジンオイルは飲める？', '車のエンジンオイルってオイルだから体にいいですよね？オリーブオイルみたいな？', 11, 41)");
            stmt.executeUpdate(
                    "INSERT INTO questions (title, content, user_id, empathy_count) VALUES ('ATMにお金入れたら増える？', 'ATMってお金増やす機械ですよね？10万円入れたら20万円になって出てきますか？', 14, 27)");
            stmt.executeUpdate(
                    "INSERT INTO questions (title, content, user_id, empathy_count) VALUES ('靴下を手袋として使う', '手袋忘れたんですけど靴下を手にはめたら暖かくないですか？同じ形だし。', 10, 13)");
            stmt.executeUpdate(
                    "INSERT INTO questions (title, content, user_id, empathy_count) VALUES ('シャンプーで食器洗える？', 'シャンプー余ってるんで食器洗剤の代わりに使っていいですか？泡立つし同じでしょ。', 9, 22)");
            stmt.executeUpdate(
                    "INSERT INTO questions (title, content, user_id, empathy_count) VALUES ('マヨネーズはスキンケア用品？', 'マヨネーズって卵とか入ってて栄養あるから顔に塗ったら美肌になりますか？', 10, 36)");
            stmt.executeUpdate(
                    "INSERT INTO questions (title, content, user_id, empathy_count) VALUES ('扇風機で涼しくならない', '扇風機つけてるのに暑いです。扇風機の前で氷食べたら冷風出ますか？', 12, 9)");
            stmt.executeUpdate(
                    "INSERT INTO questions (title, content, user_id, empathy_count) VALUES ('コンセントに指入れたら', 'コンセントの穴に指入れたらビリビリして気持ちいいですか？マッサージみたいな？', 13, 61)");
            stmt.executeUpdate(
                    "INSERT INTO questions (title, content, user_id, empathy_count) VALUES ('消しゴムで記憶も消せる？', '消しゴムで字を消せるなら、頭に当てたら嫌な記憶も消せますか？試験前にやりたい。', 15, 24)");
            stmt.executeUpdate(
                    "INSERT INTO questions (title, content, user_id, empathy_count) VALUES ('信号無視していい時間帯', '夜中って誰も見てないから信号無視していいですよね？警察も寝てるし。', 11, 17)");
            stmt.executeUpdate(
                    "INSERT INTO questions (title, content, user_id, empathy_count) VALUES ('目薬を口から飲む', '目薬って目に入れるより飲んだ方が効率よくないですか？体内から効きそう。', 10, 31)");
            stmt.executeUpdate(
                    "INSERT INTO questions (title, content, user_id, empathy_count) VALUES ('蛍光灯を日光浴に使う', '蛍光灯の下にいたら日焼けしますか？太陽と同じ光でしょ？ビタミンD作れますか？', 12, 14)");
            System.out.println("Initial questions created.");

            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (1, 'チョコレート入れるの普通です！あとリンゴとバナナも入れるとフルーティーになります。最強は納豆です。', 'ラーメン仙人', 5, 18)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (1, 'カレーにはカレールーを入れるといいですよ。', 'おにぎり職人', 6, 3)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (2, '猫は目覚まし時計じゃないので無理です。でも餌の時間にすれば起こしてくれるかも！', 'ネコ派一郎', 3, 12)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (2, '寝なければ起きる必要ないですよ。', 'ゲーム廃人', 7, 8)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (3, '電源ボタンは大体前面か上面にありますよ。叩くのはおすすめしません。優しく触ってあげてください。', 'ポチ太郎', 1, 5)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (4, '冷蔵庫は魔法じゃなくて科学です！圧縮と膨張を繰り返して熱を外に逃がしてるんですよ。でも魔法だと思った方が楽しいかも。', 'カレー好き子', 2, 22)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (5, '月曜日は絶対来ます。諦めてください。でも有給使えば来ないも同然です。', '昼寝マスター', 4, 35)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (5, '日曜日を永遠に繰り返せばいいんです。タイムループですね。', 'ゲーム廃人', 7, 19)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (6, 'コーヒーに炭酸入ってないです。味も全然違います。飲み比べてみてください。', 'ラーメン仙人', 5, 7)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (7, 'Siriとかアレクサとか使えば喋ってくれますよ。普通のスマホは喋りません。', 'ポチ太郎', 1, 4)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (8, '好きに食べればいいです。どんぶり投げるのはダメです。お店の人に怒られます。', 'ラーメン仙人', 5, 14)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (9, '睡眠削るとか絶対ダメです。ちゃんと寝てください。コーヒー100杯は死にます。', 'カレー好き子', 2, 11)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (10, 'パンダの色は遺伝子で決まってます。塗り絵したら警察に捕まります。', 'ネコ派一郎', 3, 20)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (11, 'WiFiは電波なので目に見えません。でも確実に飛んでます。信じてください。', 'おにぎり職人', 6, 6)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (12, 'お金を土に埋めても増えません。銀行に預けておくのが一番安全です。', '昼寝マスター', 4, 25)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (13, '鏡は光の反射で像を映してるだけなので、左右反転は錯覚です。壊れてないです。', 'ポチ太郎', 1, 9)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (14, '体重計は壊れてないです。現実を受け入れましょう。運動と食事制限がんばってください。', 'カレー好き子', 2, 18)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (15, 'いいえ。カビキラーはボディーソープの代用にはなりますが、乾燥の元なので顔などのデリケートゾーンには使用できません。', 'カオス博士', 12, 45)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (15, '絶対ダメです！！皮膚が溶けます！！普通に洗顔料買ってください！！！', 'ポチ太郎', 1, 38)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (16, 'ハイターは絶対ダメです。歯医者に行ってホワイトニングしてください。死にますよ。', 'ネコ派一郎', 3, 42)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (16, '重曹で磨くといいらしいですよ。ハイターは毒なのでやめてください。', 'カレー好き子', 2, 29)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (17, 'スマホ爆発します。絶対やめてください。コンセントで充電してください。', 'おにぎり職人', 6, 51)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (17, '電磁波でチンされるのはスマホじゃなくてあなたです。', 'ゲーム廃人', 7, 33)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (18, '醤油は調味料です。塩分過多で死にます。コーヒー飲んでください。', 'ラーメン仙人', 5, 24)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (18, '一口なめる程度ならいいですけど、飲むのは絶対ダメです。腎臓壊れます。', '昼寝マスター', 4, 19)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (19, '段ボールは水を吸収するので逆にびしょ濡れになります。コンビニで傘買ってください。', 'ポチ太郎', 1, 11)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (19, 'ゴミ袋被った方がまだマシです。でも傘買った方がいいです。', '天然ボケ子', 10, 8)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (20, '洗濯機に入ったら溺れて死にます。お風呂入ってください。', 'ネコ派一郎', 3, 37)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (20, '洗濯機は人間用じゃないです。回転で骨折しますよ。', 'カレー好き子', 2, 28)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (21, 'ガムは消化されないのでそのまま出てきます。栄養にはなりません。', 'おにぎり職人', 6, 15)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (21, '噛むだけじゃ痩せません。運動してください。飲み込むと腸に詰まる可能性あります。', 'ラーメン仙人', 5, 13)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (22, '無重力にはなりません。ただの普通のジャンプです。宇宙飛行士は無理です。', 'ゲーム廃人', 7, 21)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (22, 'ちょっとフワッとする感じはありますけど宇宙とは全然違います。', '昼寝マスター', 4, 18)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (23, '瞬間接着剤は絶対ダメです！！皮膚が壊死します！！病院行ってください！！', 'ポチ太郎', 1, 48)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (23, '医療用の接着剤はありますけど、瞬間接着剤は毒です。薬局で絆創膏買ってください。', 'カレー好き子', 2, 35)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (24, 'ドライヤーの温度じゃ料理は無理です。火事になるかもしれないのでやめてください。', 'ラーメン仙人', 5, 12)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (24, 'せいぜい温める程度です。生肉とか絶対焼けません。コンロ使ってください。', '天然ボケ子', 10, 9)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (25, '車のオイルは鉱物油で毒です。飲んだら救急車です。オリーブオイルとは別物です。', 'ネコ派一郎', 3, 44)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (25, 'エンジンオイルと食用オイルは全く違います。飲んだら死にます。', 'おにぎり職人', 6, 32)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (26, 'ATMはお金を預けたり引き出したりする機械です。増やす機能はありません。', '昼寝マスター', 4, 23)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (26, 'そんな機械があったら世界中の人がお金持ちになってます。投資でも勉強してください。', 'ゲーム廃人', 7, 17)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (27, '靴下は足用なので臭いですよ。100円ショップで手袋買ってください。', 'ポチ太郎', 1, 10)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (27, '形は似てるけど衛生的に問題あります。素直に手袋買いましょう。', 'カレー好き子', 2, 7)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (28, 'シャンプーは食器に使えません。成分が違います。食器洗剤買ってください。', 'おにぎり職人', 6, 16)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (28, '泡立つからって同じじゃないです。お腹壊しますよ。', 'ラーメン仙人', 5, 14)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (29, 'マヨネーズは食品です。顔に塗ったらニキビできますよ。化粧品使ってください。', 'ネコ派一郎', 3, 27)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (29, '卵パックとマヨネーズは違います。脂っこくなるだけです。', '天然ボケ子', 10, 22)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (30, '扇風機は風を送るだけです。あなたが氷食べても関係ないです。エアコン使ってください。', 'ゲーム廃人', 7, 6)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (30, '冷風は出ません。扇風機の前に氷置いたら少し涼しいかもですけど。', '昼寝マスター', 4, 5)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (31, '絶対にダメです！！！感電して死にます！！！マッサージ機買ってください！！！', 'ポチ太郎', 1, 58)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (31, '100Vで感電します。最悪死亡します。絶対やめてください。', 'カレー好き子', 2, 49)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (32, '消しゴムは物理的な摩擦で字を消してるだけです。記憶は消せません。', 'ネコ派一郎', 3, 19)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (32, '頭に当てても何も起きません。ちゃんと勉強してください。', 'おにぎり職人', 6, 15)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (33, '信号無視は犯罪です。夜中でも監視カメラあります。罰金くらいますよ。', 'ラーメン仙人', 5, 26)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (33, '警察は24時間働いてます。信号は絶対守ってください。事故ります。', 'ゲーム廃人', 7, 21)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (34, '目薬は目に使うように作られてます。飲んだら効果ないし、お腹壊すかもです。', '昼寝マスター', 4, 20)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (34, '目薬の成分は飲むものじゃないです。ちゃんと目にさしてください。', 'ポチ太郎', 1, 13)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (35, '蛍光灯と太陽光は別物です。日焼けしません。ビタミンDも作れません。', 'カレー好き子', 2, 11)");
            stmt.executeUpdate(
                    "INSERT INTO answers (question_id, answer, responder_name, user_id, empathy_count) VALUES (35, '紫外線がないと日焼けしません。外に出て太陽浴びてください。', 'ネコ派一郎', 3, 9)");
            System.out.println("Initial answers created.");

        } catch (SQLException e) {
            System.out.println("Error inserting initial data: " + e.getMessage());
        }
    }

    /**
     * テーブルにカラムを追加
     * 
     * @param conn       データベース接続
     * @param tableName  テーブル名
     * @param columnName カラム名
     * @param columnType カラムの型
     * @throws SQLException SQL例外
     */
    private static void addColumnIfNotExists(Connection conn, String tableName, String columnName, String columnType) throws SQLException {
        boolean columnExists = false;

        try (ResultSet rs = conn.getMetaData().getColumns(null, null, tableName, columnName)) {
            columnExists = rs.next();
        }

        if (!columnExists) {
            try (Statement stmt = conn.createStatement()) {
                String sql = String.format("ALTER TABLE %s ADD COLUMN %s %s", tableName, columnName, columnType);
                stmt.execute(sql);
                System.out.println("Added column " + columnName + " to table " + tableName);
            }
        }
    }
}
