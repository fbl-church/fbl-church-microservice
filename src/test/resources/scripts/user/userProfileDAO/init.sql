DELETE FROM user_profile;
DELETE FROM web_role;

INSERT INTO user_profile (id, first_name, last_name, email, web_role_id)
VALUES 
(1, 'Test', 'User', 'test@mail.com', 1),
(2, 'Bill', 'Tanner', 'billT@mail.com', 1),
(3, 'Fake', 'User', 'Fake123@mail.com', 2);

INSERT INTO web_role (id, text_id, role_rank)
VALUES 
(1, 'USER', 100),
(2, 'TEST', 200),
(9, 'ADMIN', 300);
