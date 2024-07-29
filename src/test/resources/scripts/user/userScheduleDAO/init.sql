DELETE FROM users;
DELETE FROM user_credentials;
DELETE FROM user_roles;
DELETE FROM attendance_records;
DELETE FROM attendance_record_workers;


INSERT INTO users (id, first_name, last_name, email, theme, account_status, app_access)
VALUES 
(1, 'Test', 'User', 'test@mail.com', 'LIGHT', 'ACTIVE', 1),
(2, 'Bill', 'Tanner', 'billT@mail.com', 'LIGHT', 'ACTIVE', 1),
(3, 'Fake', 'User', 'Fake123@mail.com', 'LIGHT', 'ACTIVE', 1);

INSERT INTO user_credentials (user_id, password)
VALUES 
(1, 'password1'),
(2, 'password2'),
(3, 'password3');

INSERT INTO user_roles (user_id, web_role)
VALUES
(1, 'USER'),
(2, 'USER'),
(3, "ADMIN");

INSERT INTO attendance_records (id, name, status, type, active_date, closed_date, started_by_user_id, closed_by_user_id)
VALUES 
(1, "Junior Church", "CLOSED", "JUNIOR_CHURCH" ,"2023-10-15", "2023-10-15 11:43:00", 2, 2),
(2, "Nursery (SS)", "PENDING", "NURSERY" ,"2023-10-22", null, null, null),
(3, "Junior Church", "ACTIVE", "JUNIOR_CHURCH" ,"2023-10-29", null, 1, null);

INSERT INTO attendance_record_workers (attendance_record_id, user_id)
VALUES
(1,1),
(2,1),
(3,2);

