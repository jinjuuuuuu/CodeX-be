INSERT INTO PROBLEM (problem_id, title, description, difficulty, tags, example_input, example_output)
VALUES (1, 'Sample Problem', 'This is a sample problem.', 1, 'sample', 'input', 'output');

INSERT INTO APP_USER (user_id, username) VALUES (1, 'test_user');

INSERT INTO SUBMISSION (user_id, problem_id, code, language, status)
VALUES
(1, 1, 'print("Hello World")', 'python', 'PASS');