ALTER TABLE notes ADD user_id INT REFERENCES users(id);

UPDATE notes SET user_id = 1 WHERE user_id IS NULL;

ALTER TABLE notes ALTER COLUMN user_id SET NOT NULL;