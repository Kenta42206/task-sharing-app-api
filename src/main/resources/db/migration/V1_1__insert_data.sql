-- 初期値を挿入する

-- users
INSERT INTO users (id, username, email, password, created_at) VALUES
(1, 'user1', 'a@a.com', '$2b$12$jTEfDZmyTYa0iY3ewUCkVe8fnuYQuMsMKAI93/qZXVfSU7DUynni2', NOW()),
(2, 'user2', 'b@b.com', '$2b$12$jTEfDZmyTYa0iY3ewUCkVe8fnuYQuMsMKAI93/qZXVfSU7DUynni2', NOW()),
(3, 'user3', 'c@c.com', '$2b$12$jTEfDZmyTYa0iY3ewUCkVe8fnuYQuMsMKAI93/qZXVfSU7DUynni2', NOW());

-- statuses
INSERT INTO statuses (id, name) VALUES
(1, '未着手'),
(2, '進行中'),
(3, '完了'),
(4, '保留');

-- categories
INSERT INTO categories (id, name) VALUES
(1, '開発'),
(2, '会議'),
(3, '定型業務');

INSERT INTO rooms (id, name, description)
VALUES
    (1,'開発チーム', '開発チームのタスク管理用ルーム'),
    (2,'マーケティングチーム', 'マーケティングチームのタスク管理用ルーム');

-- tasks
INSERT INTO tasks (user_id, title, description, status_id, category_id, room_id, importance, progress, priority, due_date, created_at)
VALUES
    (1, 'タスク1', 'タスク1の説明', 1, 1, 1, 3, 20, 2, '2024-11-15', now()),
    (2, 'タスク2', 'タスク2の説明', 2, 1, 2, 4, 50, 1, '2024-11-20', now()),
    (1, 'タスク3', 'タスク3の説明', 1, 2, 1, 2, 0, 3, '2024-11-25', now()),
    (3, 'タスク4', 'タスク4の説明', 3, 2, 2, 5, 80, 4, '2024-11-30', now());


INSERT INTO user_rooms (user_id, room_id)
VALUES
    (1, 1),  -- ユーザー1が開発チームに参加
    (1, 2),  -- ユーザー1がマーケティングチームに参加
    (2, 1),  -- ユーザー2が開発チームに参加
    (3, 2);
