DROP TABLE IF EXISTS categories;

CREATE TABLE categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    hidden BOOLEAN DEFAULT false
);

ALTER TABLE tasks ADD FOREIGN KEY (category_id) REFERENCES categories(id);