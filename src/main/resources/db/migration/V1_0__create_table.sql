-- users テーブルの作成
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- statuses テーブルの作成
CREATE TABLE IF NOT EXISTS statuses (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    description TEXT
);

-- categories テーブルの作成
CREATE TABLE IF NOT EXISTS categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    description TEXT
);

-- rooms テーブルの作成
CREATE TABLE IF NOT EXISTS rooms (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- user_rooms テーブルの作成 (多対多の関係を管理)
CREATE TABLE IF NOT EXISTS user_rooms (
    user_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
    room_id INTEGER REFERENCES rooms(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, room_id)  -- 複合プライマリーキー
);

-- tasks テーブルの作成
CREATE TABLE IF NOT EXISTS tasks (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    status_id INTEGER REFERENCES statuses(id),
    category_id INTEGER REFERENCES categories(id),
    room_id INTEGER REFERENCES rooms(id) ON DELETE CASCADE,  -- ルームとのリレーション
    importance INTEGER CHECK (importance BETWEEN 1 AND 5),
    progress INTEGER CHECK (progress BETWEEN 0 AND 100) DEFAULT 0,
    priority INTEGER CHECK (priority BETWEEN 1 AND 5) DEFAULT 3,
    due_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS locks (
    task_id INTEGER REFERENCES tasks(id) ON DELETE CASCADE,
    user_id INTEGER REFERENCES users(id) ON DELETE CASCADE, 
    lock_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (task_id, user_id)
);

-- シーケンスの作成 (初期値を10に設定)
CREATE SEQUENCE IF NOT EXISTS users_id_seq START 10;
CREATE SEQUENCE IF NOT EXISTS statuses_id_seq START 10;
CREATE SEQUENCE IF NOT EXISTS categories_id_seq START 10;
CREATE SEQUENCE IF NOT EXISTS rooms_id_seq START 10;
CREATE SEQUENCE IF NOT EXISTS tasks_id_seq START 10;

-- シーケンスを各テーブルに関連付ける
ALTER TABLE users ALTER COLUMN id SET DEFAULT nextval('users_id_seq');
ALTER TABLE statuses ALTER COLUMN id SET DEFAULT nextval('statuses_id_seq');
ALTER TABLE categories ALTER COLUMN id SET DEFAULT nextval('categories_id_seq');
ALTER TABLE rooms ALTER COLUMN id SET DEFAULT nextval('rooms_id_seq');
ALTER TABLE tasks ALTER COLUMN id SET DEFAULT nextval('tasks_id_seq');