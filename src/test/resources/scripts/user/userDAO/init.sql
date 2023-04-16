DELETE FROM users;

INSERT INTO users (id, first_name, last_name, email, web_role)
VALUES 
(1, 'Test', 'User', 'test@mail.com', 'USER'),
(2, 'Bill', 'Tanner', 'billT@mail.com', 'USER'),
(3, 'Fake', 'User', 'Fake123@mail.com', 'ADMIN');
