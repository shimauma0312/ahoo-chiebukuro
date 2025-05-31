# Docker開発環境

## 前提条件

- Docker
- Docker Compose

## 開発環境の起動方法

開発環境を起動するには、以下のコマンドを実行してください：

```bash
docker compose up
```

これにより、アプリケーションが起動し、ソースコードの変更がホットリロードされる開発環境が起動します。

バックグラウンドで実行する場合は：

```bash
docker compose up -d
```

## 環境へのアクセス

- アプリケーション: http://localhost:8080

## SQLiteの操作

コンテナ内でSQLiteを操作するには、以下のコマンドを実行します：

```bash
docker compose exec app sqlite3 /app/data/app.db
```

SQLiteコマンドラインが起動したら、以下のようなコマンドが使用できます：

```
.tables           # テーブル一覧の表示
.schema questions # questionsテーブルのスキーマ表示
SELECT * FROM questions; # データの表示
.exit            # 終了
```

## その他のコマンド

- コンテナの停止：

```bash
docker compose down
```

- コンテナの再ビルド：

```bash
docker compose build
```

- ログの表示：

```bash
docker compose logs -f
```
