-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- Script: V1.1.2.5__Add_Children_Gurdians_Table.sql
-- Author: Sam Butler
-- Date: April 24, 2022
-- Version: v1.2.5
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- ---------------------------------------------------------------------------------
-- START
-- ---------------------------------------------------------------------------------

CREATE TABLE children_guardians (
  child_id     INT          UNSIGNED NOT NULL,
  guardian_id   INT          UNSIGNED NOT NULL,
  relationship VARCHAR(128)          NOT NULL DEFAULT 'OTHER',
  PRIMARY KEY (child_id, guardian_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX IX_children_guardians__guardian_id ON children_guardians(guardian_id);

ALTER TABLE children_guardians ADD CONSTRAINT FK1_children_guardians__children_user
  FOREIGN KEY (child_id) REFERENCES children (user_id) 
    ON DELETE CASCADE 
    ON UPDATE CASCADE;

ALTER TABLE children_guardians ADD CONSTRAINT FK2_children_guardians__guardians_user
  FOREIGN KEY (guardian_id) REFERENCES guardians (user_id) 
    ON DELETE CASCADE 
    ON UPDATE RESTRICT;

-- ---------------------------------------------------------------------------------
-- END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION


