-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- Script: V1.1.2.6__Add_Children_Gurdians_Table.sql
-- Author: Sam Butler
-- Date: April 24, 2022
-- Version: v1.1.2
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- ---------------------------------------------------------------------------------
-- START
-- ---------------------------------------------------------------------------------

CREATE TABLE children_gurdians (
  child_id     INT          UNSIGNED NOT NULL,
  gurdian_id   INT          UNSIGNED NOT NULL,
  relationship VARCHAR(128)          NOT NULL DEFAULT 'OTHER',
  PRIMARY KEY (child_id, gurdian_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX IX_children_gurdians__gurdian_id ON children_gurdians(gurdian_id);

ALTER TABLE children_gurdians ADD CONSTRAINT FK1_children_gurdians__children_user
  FOREIGN KEY (child_id) REFERENCES children (user_id) 
    ON DELETE CASCADE 
    ON UPDATE CASCADE;

ALTER TABLE children_gurdians ADD CONSTRAINT FK2_children_gurdians__gurdians_user
  FOREIGN KEY (gurdian_id) REFERENCES gurdians (user_id) 
    ON DELETE CASCADE 
    ON UPDATE RESTRICT;

-- ---------------------------------------------------------------------------------
-- END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION


