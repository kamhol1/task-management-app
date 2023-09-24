CREATE TYPE role_enum AS ENUM ('USER', 'ADMIN');

ALTER TABLE users ADD role role_enum DEFAULT 'USER' NOT NULL;

INSERT INTO users (id, first_name, last_name, username, password, role) VALUES
    (1, 'Admin', 'Admin', 'admin', '$2a$12$OsGKKTdxbd1TVrANHhEfSOP1mo5qm4SBrBPQYhymgEY9GL5m30ONC', 'ADMIN'),
    (2, 'User', 'User', 'user', '$2a$12$CjiV7yJ0sYXEj7DbBbc5qu6tEjlEApRwXYvElfB23R/9eDuFf9lVK', 'USER');