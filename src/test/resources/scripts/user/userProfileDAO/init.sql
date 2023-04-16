DELETE FROM user_profile;

INSERT INTO user_profile (id, first_name, last_name, email, web_role, church_group)
VALUES 
(1, 'Test', 'User', 'test@mail.com', 'USER', 'CUBBIES'),
(2, 'Bill', 'Tanner', 'billT@mail.com', 'USER', 'SPARKS'),
(3, 'Fake', 'User', 'Fake123@mail.com', 'ADMIN', 'SPARKS');
