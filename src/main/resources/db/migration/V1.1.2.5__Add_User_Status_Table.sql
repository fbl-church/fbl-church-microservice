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
  user_id         INT          unsigned NOT NULL,
  account_status  VARCHAR(128)          NOT NULL DEFAULT 'PENDING',
  app_access      TINYINT(4)   unsigned NOT NULL DEFAULT 0,
  updated_user_id INT          unsigned          DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE user_status 
  ADD CONSTRAINT users__user_status__FK1 FOREIGN KEY(user_id) REFERENCES users(id)
    ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE user_status
  ADD CONSTRAINT users__user_status__FK2 FOREIGN KEY(updated_user_id) REFERENCES users(id)
    ON DELETE SET NULL ON UPDATE CASCADE;
-- ---------------------------------------------------------------------------------
-- END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION