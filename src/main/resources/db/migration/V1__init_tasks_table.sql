CREATE TYPE status_enum AS ENUM ('NEW', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED');
CREATE TYPE priority_enum AS ENUM ('LOW', 'MEDIUM', 'HIGH');

DROP TABLE IF EXISTS tasks;

CREATE TABLE tasks (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    category_id INT NOT NULL,
    status status_enum,
    priority priority_enum NOT NULL,
    target_time TIMESTAMP,
    created_on TIMESTAMP DEFAULT current_timestamp NOT NULL,
    updated_on TIMESTAMP DEFAULT current_timestamp NOT NULL
);