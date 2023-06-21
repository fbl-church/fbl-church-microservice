DELETE FROM users;
DELETE FROM user_credentials;
DELETE FROM user_status;
DELETE FROM user_roles;

INSERT INTO users (id, first_name, last_name, email)
VALUES 
(1, 'Test', 'User', 'test@mail.com'),
(2, 'Bill', 'Tanner', 'billT@mail.com'),
(3, 'Fake', 'User', 'Fake123@mail.com');

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

INSERT INTO user_roles (user_id, web_role)
VALUES
(1, 'USER'),
(2, 'USER'),
(3, "ADMIN");