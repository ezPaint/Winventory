-- Remove Original Table and Sequence

DROP TABLE TEAM;
DROP SEQUENCE TEAM_SEQ;
-- Create Table


CREATE TABLE TEAM (
    KEY                NUMBER(10) NOT NULL,
    NAME               VARCHAR2(30) NOT NULL,
    IS_ACTIVE          NUMBER(1),
    LEADER_ID          NUMBER(10),
    CONSTRAINT TEAM_PK PRIMARY KEY ( KEY ));

CREATE SEQUENCE TEAM_SEQ;


-- Sample Select Statement

SELECT
    KEY, NAME, IS_ACTIVE, LEADER_ID 
from TEAM
WHERE
    KEY = 0;

-- PROTECTED CODE -->