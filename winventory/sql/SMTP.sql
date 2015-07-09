-- Remove Original Table and Sequence

DROP TABLE SMTP;
DROP SEQUENCE SMTP_SEQ;
-- Create Table


CREATE TABLE SMTP (
    KEY                NUMBER(10) NOT NULL,
    HOST_NAME          VARCHAR2(50),
    PORT               NUMBER(10),
    AUTH_USER_NAME     VARCHAR2(50),
    AUTH_PASSWORD      VARCHAR2(50),
    SSL                NUMBER(1),
    CONSTRAINT SMTP_PK PRIMARY KEY ( KEY ));

CREATE SEQUENCE SMTP_SEQ;


-- Sample Select Statement

SELECT
    KEY, HOST_NAME, PORT, AUTH_USER_NAME, AUTH_PASSWORD, SSL 
from SMTP
WHERE
    KEY = 0;

-- PROTECTED CODE -->