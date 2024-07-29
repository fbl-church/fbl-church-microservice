DELETE FROM users;
DELETE FROM user_credentials;
DELETE FROM user_roles;

INSERT INTO users (id, first_name, last_name, email, theme, account_status, app_access)
VALUES (1, 'Auth', 'User', 'test@mail.com', 'LIGHT', 'ACTIVE', 1);


INSERT INTO user_credentials(user_id, password)
VALUES (1, '$2a$10$KusdNWjdceySzNAG3EH8a.5HuIOMWH4hl4Ke64Daqaeqivy1y0Rd.');


INSERT INTO user_roles (user_id, web_role)
VALUES (1, 'USER');