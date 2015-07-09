-- Remove Original Table and Sequence

DROP TABLE ACCESS_TOKEN;
-- Create Table


CREATE TABLE ACCESS_TOKEN (
    USER_KEY           NUMBER(10) NOT NULL NOT NULL,
    TOKEN              VARCHAR2(200) NOT NULL,
    EXPIRATION         DATE NOT NULL,
    CONSTRAINT ACCESS_TOKEN_PK PRIMARY KEY ( USER_KEY ));


-- Sample Select Statement

SELECT
    USER_KEY, TOKEN, EXPIRATION 
from ACCESS_TOKEN
WHERE
    USER_KEY = 0;

-- PROTECTED CODE -->