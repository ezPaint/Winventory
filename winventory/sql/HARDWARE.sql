-- Remove Original Table and Sequence

DROP TABLE HARDWARE;
DROP SEQUENCE HARDWARE_SEQ;
-- Create Table


CREATE TABLE HARDWARE (
    KEY                NUMBER(10) NOT NULL,
    TYPE               VARCHAR2(40),
    DESCRIPTION        VARCHAR2(2000),
    COST               NUMBER(12,2),
    SERIAL_NO          VARCHAR2(40),
    CONDITION          VARCHAR2(40),
    LOCATION_ID        NUMBER(10),
    USER_ID            NUMBER(10),
    PURCHASE_DATE      DATE,
    CONSTRAINT HARDWARE_PK PRIMARY KEY ( KEY ));

CREATE SEQUENCE HARDWARE_SEQ;


-- Sample Select Statement

SELECT
    KEY, TYPE, DESCRIPTION, COST, SERIAL_NO, CONDITION, LOCATION_ID, USER_ID, PURCHASE_DATE 
from HARDWARE
WHERE
    KEY = 0;

-- PROTECTED CODE -->