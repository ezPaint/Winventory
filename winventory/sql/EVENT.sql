-- Remove Original Table and Sequence

DROP TABLE EVENT;
DROP SEQUENCE EVENT_SEQ;
-- Create Table


CREATE TABLE EVENT (
    KEY                NUMBER(10) NOT NULL,
    DATE_CREATED       DATE,
    DESCRIPTION        VARCHAR2(2000),
    CATEGORY           VARCHAR2(50),
    CONSTRAINT EVENT_PK PRIMARY KEY ( KEY ));

CREATE SEQUENCE EVENT_SEQ;


-- Sample Select Statement

SELECT
    KEY, DATE_CREATED, DESCRIPTION, CATEGORY 
from EVENT
WHERE
    KEY = 0;

-- PROTECTED CODE -->