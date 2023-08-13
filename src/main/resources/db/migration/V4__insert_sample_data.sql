INSERT INTO categories (id, name) VALUES
    (1, 'Development'),
    (2, 'Testing'),
    (3, 'Support'),
    (4, 'Security');

INSERT INTO tasks (id, title, description, category_id, status, priority, target_time) VALUES
    (1, 'Test task', 'Test task description', 1, 'NEW', 'LOW', CURRENT_TIMESTAMP),
    (2, 'Task with medium priority', 'Description', 2, 'COMPLETED', 'MEDIUM', CURRENT_TIMESTAMP),
    (3, 'Easy task', 'Very easy', 4, 'IN_PROGRESS', 'LOW', null),
    (4, 'Impossible task', 'Hard task', 3, 'CANCELLED', 'HIGH', null);

INSERT INTO notes (id, content, task_id) VALUES
    (1, 'Note number one', 1),
    (2, 'Note number two', 1),
    (3, 'Note number three', 2),
    (4, 'Note number four', 3);

ALTER SEQUENCE categories_id_seq RESTART WITH 5;
ALTER SEQUENCE tasks_id_seq RESTART WITH 5;
ALTER SEQUENCE notes_id_seq RESTART WITH 5;