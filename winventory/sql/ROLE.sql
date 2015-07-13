-- Remove Original Table and Sequence

DROP TABLE ROLE;
DROP SEQUENCE ROLE_SEQ;
-- Create Table


CREATE TABLE ROLE (
    KEY                NUMBER(10) NOT NULL,
    TITLE              VARCHAR2(30),
    CONSTRAINT ROLE_PK PRIMARY KEY ( KEY ));


-- Sample Select Statement

SELECT
    KEY, TITLE 
from ROLE
WHERE
    KEY = 0;

-- PROTECTED CODE -->