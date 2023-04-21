DELETE FROM users;
DELETE FROM user_credentials;
DELETE FROM user_status;

INSERT INTO users (id, first_name, last_name, email, web_role)
VALUES 
(1, 'Test', 'User', 'test@mail.com', 'USER'),
(2, 'Bill', 'Tanner', 'billT@mail.com', 'USER'),
(3, 'Fake', 'User', 'Fake123@mail.com', 'ADMIN');

INSERT INTO user_credentials (user_id, password)
VALUES 
(1, 'password1'),
(2, 'password2'),
(3, 'password3');

INSERT INTO user_status (user_id, account_status, app_access, updated_user_id)
VALUES 
(1, 'APPROVED', 1, 1),
(2, 'APPROVED', 1, 1),
(3, 'APPROVED', 1, 1);
