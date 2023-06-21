DELETE FROM users;
DELETE FROM user_credentials;
DELETE FROM user_status;
DELETE FROM user_roles;

INSERT INTO users (id, first_name, last_name, email)
VALUES (1, 'Auth', 'User', 'test@mail.com');


INSERT INTO user_credentials(user_id, password)
VALUES (1, '$2a$10$KusdNWjdceySzNAG3EH8a.5HuIOMWH4hl4Ke64Daqaeqivy1y0Rd.');

INSERT INTO user_status (user_id, account_status, app_access, updated_user_id)
VALUES (1, 'APPROVED', 1, 1);

INSERT INTO user_roles (user_id, web_role)
VALUES (1, 'USER');