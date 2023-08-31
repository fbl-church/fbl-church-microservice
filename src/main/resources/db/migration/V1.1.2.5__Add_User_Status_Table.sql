-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- Script: V1.1.0.3__Add_User_Status_Table.sql
-- Author: Sam Butler
-- Date: April 24, 2022
-- Version: V1.1.0
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- ---------------------------------------------------------------------------------
-- START
-- ---------------------------------------------------------------------------------

CREATE TABLE user_status (
  user_id         INT          UNSIGNED NOT NULL,
  account_status  VARCHAR(128)          NOT NULL DEFAULT 'PENDING',
  app_access      TINYINT(4)   UNSIGNED NOT NULL DEFAULT 0,
  updated_user_id INT          UNSIGNED          DEFAULT NULL,
  PRIMARY KEY (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX IX_user_status__updated_user_id ON user_status(updated_user_id);

ALTER TABLE user_status ADD CONSTRAINT FK1_user_status__users
  FOREIGN KEY(user_id) REFERENCES users(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE;

ALTER TABLE user_status ADD CONSTRAINT FK2_user_status__users
  FOREIGN KEY(updated_user_id) REFERENCES users(id)
    ON DELETE SET NULL
    ON UPDATE CASCADE;

-- ---------------------------------------------------------------------------------
-- END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION