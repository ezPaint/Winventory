-- Remove Original Table and Sequence

DROP TABLE ADDRESS;
DROP SEQUENCE ADDRESS_SEQ;
-- Create Table


CREATE TABLE ADDRESS (
    KEY                NUMBER(10) NOT NULL,
    STREET1            VARCHAR2(50),
    STREET2            VARCHAR2(50),
    CITY               VARCHAR2(50),
    STATE              VARCHAR2(50),
    ZIPCODE            VARCHAR2(10),
    CONSTRAINT ADDRESS_PK PRIMARY KEY ( KEY ));

CREATE SEQUENCE ADDRESS_SEQ;


-- Sample Select Statement

SELECT
    KEY, STREET1, STREET2, CITY, STATE, ZIPCODE 
from ADDRESS
WHERE
    KEY = 0;

-- PROTECTED CODE -->