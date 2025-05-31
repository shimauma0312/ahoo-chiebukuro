# Docker開発環境の使用方法

このプロジェクトはDockerを使用して簡単に開発環境を構築できます。

## 前提条件

- Docker
- Docker Compose

## 開発環境の起動方法

1. 開発環境を起動する（たった一つのコマンドで完了）:

```bash
docker-compose up
```

これだけで開発環境が起動し、ソースコードの変更がホットリロードされます。

## 環境へのアクセス

- アプリケーション: http://localhost:8080

## その他のコマンド

- コンテナをバックグラウンドで実行:

```bash
docker-compose up -d
```

- コンテナの停止:

```bash
docker-compose down
```

- コンテナの再ビルド（設定変更時など）:

```bash
docker-compose build
```

- ログの表示:

```bash
docker-compose logs -f
```

## SQLiteについて

- SQLiteのデータベースファイルは`/app/data/app.db`に保存されます
- データはDockerボリュームに保存されるため、コンテナを再起動しても保持されます
- ホストマシンとのファイル共有が設定されているため、IDEからのコード変更がすぐに反映されます
