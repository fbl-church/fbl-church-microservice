DELETE FROM user_credentials;
DELETE FROM user_profile;
DELETE FROM web_role;

INSERT INTO user_profile (id, first_name, last_name, email, web_role_id)
VALUES 
(1, 'Test', 'User', 'test@mail.com', 1),
(2, 'Bill', 'Tanner', 'billT@mail.com', 1);

INSERT INTO user_credentials(user_id, password)
VALUES (1, '$2a$10$KusdNWjdceySzNAG3EH8a.5HuIOMWH4hl4Ke64Daqaeqivy1y0Rd.');

INSERT INTO web_role (id, text_id, role_rank)
VALUES 
(1, 'USER', 100),
(2, 'TEST', 200),
(9, 'ADMIN', 300);