-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- Script: V1.1.3.1__Add_Feature_Access_Table.sql
-- Author: Sam Butler
-- Date: April 24, 2022
-- Issue: FBL-2: Create Web Role Table 
-- Version: V1.1.3.1
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- ---------------------------------------------------------------------------------
-- FBL-2: START
-- ---------------------------------------------------------------------------------

CREATE TABLE feature_access (
  id                       INT          unsigned NOT NULL AUTO_INCREMENT,
  feature_application_text VARCHAR(128)          NOT NULL,
  feature_name_text        VARCHAR(128)          NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ---------------------------------------------------------------------------------
-- FBL-2: END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION