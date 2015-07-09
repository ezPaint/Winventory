-- Remove Original Table and Sequence

DROP TABLE SOFTWARE;
DROP SEQUENCE SOFTWARE_SEQ;
-- Create Table


CREATE TABLE SOFTWARE (
    KEY                NUMBER(10) NOT NULL,
    NAME               VARCHAR2(30),
    SERIAL_NO          VARCHAR2(30),
    VERSION            VARCHAR2(20),
    LICENSE_KEY        VARCHAR2(30),
    DESCRIPTION        VARCHAR2(2000),
    COST               NUMBER(12,2),
    PURCHASED_DATE     DATE,
    EXPIRATION_DATE    DATE,
    CONSTRAINT SOFTWARE_PK PRIMARY KEY ( KEY ));

CREATE SEQUENCE SOFTWARE_SEQ;


-- Sample Select Statement

SELECT
    KEY, NAME, SERIAL_NO, VERSION, LICENSE_KEY, DESCRIPTION, COST, PURCHASED_DATE, EXPIRATION_DATE 
from SOFTWARE
WHERE
    KEY = 0;

-- PROTECTED CODE -->