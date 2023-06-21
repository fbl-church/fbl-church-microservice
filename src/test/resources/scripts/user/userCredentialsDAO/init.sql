DELETE FROM user_credentials;
DELETE FROM users;
DELETE FROM user_roles;

INSERT INTO users (id, first_name, last_name, email)
VALUES 
(1, 'Test', 'User', 'test@mail.com'),
(2, 'Bill', 'Tanner', 'billT@mail.com');

INSERT INTO user_credentials(user_id, password)
VALUES (1, '$2a$10$KusdNWjdceySzNAG3EH8a.5HuIOMWH4hl4Ke64Daqaeqivy1y0Rd.');

INSERT INTO user_roles (user_id, web_role)
VALUES
(1, 'USER'),
(2, 'USER');