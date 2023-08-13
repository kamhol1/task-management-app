DROP TABLE IF EXISTS notes;

CREATE TABLE notes (
    id SERIAL PRIMARY KEY,
    content TEXT NOT NULL,
    task_id INT NOT NULL,
    created_on TIMESTAMP DEFAULT current_timestamp NOT NULL,
    updated_on TIMESTAMP DEFAULT current_timestamp NOT NULL,
    FOREIGN KEY (task_id) REFERENCES tasks(id) ON DELETE CASCADE
);