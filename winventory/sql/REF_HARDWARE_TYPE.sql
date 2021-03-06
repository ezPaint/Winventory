-- Remove Original Table and Sequence

DROP TABLE REF_HARDWARE_TYPE;
-- Create Table


CREATE TABLE REF_HARDWARE_TYPE (
    CODE               VARCHAR2(40) NOT NULL NOT NULL,
    DESCRIPTION        VARCHAR2(2000) NOT NULL,
    CONSTRAINT REF_HARDWARE_TYPE_PK PRIMARY KEY ( CODE ));


-- Sample Select Statement

SELECT
    CODE, DESCRIPTION 
from REF_HARDWARE_TYPE
WHERE
    CODE = 0;

-- PROTECTED CODE -->