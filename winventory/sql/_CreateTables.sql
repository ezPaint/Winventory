
CREATE TABLE ACCESS_TOKEN (
    USER_KEY           NUMBER(10) NOT NULL NOT NULL,
    TOKEN              VARCHAR2(200) NOT NULL,
    EXPIRATION         DATE NOT NULL,
    CONSTRAINT ACCESS_TOKEN_PK PRIMARY KEY ( USER_KEY ));


CREATE TABLE ADDRESS (
    KEY                NUMBER(10) NOT NULL,
    STREET1            VARCHAR2(50),
    STREET2            VARCHAR2(50),
    CITY               VARCHAR2(50),
    STATE              VARCHAR2(50),
    ZIPCODE            VARCHAR2(10),
    CONSTRAINT ADDRESS_PK PRIMARY KEY ( KEY ));

CREATE SEQUENCE ADDRESS_SEQ;


CREATE TABLE EVENT (
    KEY                NUMBER(10) NOT NULL,
    DATE_CREATED       DATE,
    DESCRIPTION        VARCHAR2(2000),
    CATEGORY           VARCHAR2(50),
    CONSTRAINT EVENT_PK PRIMARY KEY ( KEY ));

CREATE SEQUENCE EVENT_SEQ;


CREATE TABLE EVENT_TO_HARDWARE (
    KEY                NUMBER(10) NOT NULL,
    EVENT_ID           NUMBER(10) NOT NULL,
    HARDWARE_ID        NUMBER(10) NOT NULL,
    CONSTRAINT EVENT_TO_HARDWARE_PK PRIMARY KEY ( KEY ));

CREATE SEQUENCE EVENT_TO_HARDWARE_SEQ;


CREATE TABLE EVENT_TO_LOCATION (
    KEY                NUMBER(10) NOT NULL,
    EVENT_ID           NUMBER(10) NOT NULL,
    LOCATION_ID        NUMBER(10) NOT NULL,
    CONSTRAINT EVENT_TO_LOCATION_PK PRIMARY KEY ( KEY ));

CREATE SEQUENCE EVENT_TO_LOCATION_SEQ;


CREATE TABLE EVENT_TO_SOFTWARE (
    KEY                NUMBER(10) NOT NULL,
    EVENT_ID           NUMBER(10) NOT NULL,
    SOFTWARE_ID        NUMBER(10) NOT NULL,
    CONSTRAINT EVENT_TO_SOFTWARE_PK PRIMARY KEY ( KEY ));

CREATE SEQUENCE EVENT_TO_SOFTWARE_SEQ;


CREATE TABLE EVENT_TO_USER (
    KEY                NUMBER(10) NOT NULL,
    EVENT_ID           NUMBER(10) NOT NULL,
    USER_ID            NUMBER(10) NOT NULL,
    CONSTRAINT EVENT_TO_USER_PK PRIMARY KEY ( KEY ));

CREATE SEQUENCE EVENT_TO_USER_SEQ;


CREATE TABLE GOOGLE_CLIENT (
    KEY                NUMBER(10) NOT NULL,
    CLIENT_ID          VARCHAR2(100),
    CLIENT_SECRET      VARCHAR2(50),
    CONSTRAINT GOOGLE_CLIENT_PK PRIMARY KEY ( KEY ));

CREATE SEQUENCE GOOGLE_CLIENT_SEQ;


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


CREATE TABLE HARDWARE_TO_SOFTWARE (
    KEY                NUMBER(10) NOT NULL,
    HARDWARE_ID        NUMBER(10) NOT NULL,
    SOFTWARE_ID        NUMBER(10) NOT NULL,
    CONSTRAINT HARDWARE_TO_SOFTWARE_PK PRIMARY KEY ( KEY ));

CREATE SEQUENCE HARDWARE_TO_SOFTWARE_SEQ;


CREATE TABLE LOCATION (
    KEY                NUMBER(10) NOT NULL,
    DESCRIPTION        VARCHAR2(2000) NOT NULL,
    IS_ACTIVE          NUMBER(1) NOT NULL,
    ADDRESS_ID         NUMBER(10) NOT NULL,
    CONSTRAINT LOCATION_PK PRIMARY KEY ( KEY ));

CREATE SEQUENCE LOCATION_SEQ;


CREATE TABLE REF_CONDITION (
    CODE               VARCHAR2(40) NOT NULL NOT NULL,
    DESCRIPTION        VARCHAR2(2000) NOT NULL,
    CONSTRAINT REF_CONDITION_PK PRIMARY KEY ( CODE ));


CREATE TABLE REF_HARDWARE_TYPE (
    CODE               VARCHAR2(40) NOT NULL NOT NULL,
    DESCRIPTION        VARCHAR2(2000) NOT NULL,
    CONSTRAINT REF_HARDWARE_TYPE_PK PRIMARY KEY ( CODE ));


CREATE TABLE REF_PERMISSION (
    KEY                NUMBER(10) NOT NULL,
    CODE               VARCHAR2(40) NOT NULL,
    DESCRIPTION        VARCHAR2(2000) NOT NULL,
    CONSTRAINT REF_PERMISSION_PK PRIMARY KEY ( KEY ));

CREATE SEQUENCE REF_PERMISSION_SEQ;


CREATE TABLE REF_PERMISSION_TO_ROLE (
    KEY                NUMBER(10) NOT NULL,
    PERMISSION_ID      NUMBER(10) NOT NULL,
    ROLE_ID            NUMBER(10) NOT NULL,
    CONSTRAINT REF_PERMISSION_TO_ROLE_PK PRIMARY KEY ( KEY ));

CREATE SEQUENCE REF_PERMISSION_TO_ROLE_SEQ;


CREATE TABLE ROLE (
    KEY                NUMBER(10) NOT NULL,
    TITLE              VARCHAR2(30),
    CONSTRAINT ROLE_PK PRIMARY KEY ( KEY ));

CREATE SEQUENCE ROLE_SEQ;


CREATE TABLE ROLE_TO_USER (
    KEY                NUMBER(10) NOT NULL,
    ROLE_ID            NUMBER(10) NOT NULL,
    USER_ID            NUMBER(10) NOT NULL,
    CONSTRAINT ROLE_TO_USER_PK PRIMARY KEY ( KEY ));

CREATE SEQUENCE ROLE_TO_USER_SEQ;


CREATE TABLE SMTP (
    KEY                NUMBER(10) NOT NULL,
    HOST_NAME          VARCHAR2(50),
    PORT               NUMBER(10),
    AUTH_USER_NAME     VARCHAR2(50),
    AUTH_PASSWORD      VARCHAR2(50),
    SSL                NUMBER(1),
    CONSTRAINT SMTP_PK PRIMARY KEY ( KEY ));

CREATE SEQUENCE SMTP_SEQ;


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


CREATE TABLE TEAM (
    KEY                NUMBER(10) NOT NULL,
    NAME               VARCHAR2(30) NOT NULL,
    IS_ACTIVE          NUMBER(1),
    LEADER_ID          NUMBER(10),
    CONSTRAINT TEAM_PK PRIMARY KEY ( KEY ));

CREATE SEQUENCE TEAM_SEQ;


CREATE TABLE TEAM_TO_USER (
    KEY                NUMBER(10) NOT NULL,
    USER_ID            NUMBER(10) NOT NULL,
    TEAM_ID            NUMBER(10) NOT NULL,
    CONSTRAINT TEAM_TO_USER_PK PRIMARY KEY ( KEY ));

CREATE SEQUENCE TEAM_TO_USER_SEQ;


CREATE TABLE USER (
    KEY                NUMBER(10) NOT NULL,
    USERNAME           VARCHAR2(40),
    PASSWORD           VARCHAR2(200) NOT NULL,
    FIRST_NAME         VARCHAR2(40),
    LAST_NAME          VARCHAR2(40),
    EMAIL              VARCHAR2(40) NOT NULL,
    CELL_PHONE         VARCHAR2(40),
    WORK_PHONE         VARCHAR2(40),
    IS_ACTIVE          NUMBER(1),
    ROLE_ID            NUMBER(10) NOT NULL,
    CONSTRAINT USER_PK PRIMARY KEY ( KEY ));

CREATE SEQUENCE USER_SEQ;

