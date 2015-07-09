-- Remove Original Table and Sequence

DROP TABLE LOCATION;
DROP SEQUENCE LOCATION_SEQ;
-- Create Table


CREATE TABLE LOCATION (
    KEY                NUMBER(10) NOT NULL,
    DESCRIPTION        VARCHAR2(2000) NOT NULL,
    IS_ACTIVE          NUMBER(1) NOT NULL,
    ADDRESS_ID         NUMBER(10) NOT NULL,
    CONSTRAINT LOCATION_PK PRIMARY KEY ( KEY ));

CREATE SEQUENCE LOCATION_SEQ;


-- Sample Select Statement

SELECT
    KEY, DESCRIPTION, IS_ACTIVE, ADDRESS_ID 
from LOCATION
WHERE
    KEY = 0;

-- PROTECTED CODE -->