CREATE TABLE PROBLEM (
    problem_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title TEXT NOT NULL,
    description TEXT NOT NULL,
    difficulty INT NOT NULL,
    tags TEXT,
    example_input TEXT,
    example_output TEXT
);

CREATE TABLE APP_USER (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE SUBMISSION (
    submission_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    problem_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    code TEXT NOT NULL,
    language VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    submitted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (problem_id) REFERENCES PROBLEM(problem_id),
    FOREIGN KEY (user_id) REFERENCES APP_USER(user_id)
);