DROP TABLE ACCESS_TOKEN;
DROP SEQUENCE ACCESS_TOKEN_SEQ;

DROP TABLE ADDRESS;
DROP SEQUENCE ADDRESS_SEQ;

DROP TABLE EVENT;
DROP SEQUENCE EVENT_SEQ;

DROP TABLE EVENT_TO_LOCATION;
DROP SEQUENCE EVENT_TO_LOCATION_SEQ;

DROP TABLE EVENT_TO_USER;
DROP SEQUENCE EVENT_TO_USER_SEQ;

DROP TABLE HARDWARE;
DROP SEQUENCE HARDWARE_SEQ;

DROP TABLE HARDWARE_TO_SOFTWARE;
DROP SEQUENCE HARDWARE_TO_SOFTWARE_SEQ;

DROP TABLE LOCATION;
DROP SEQUENCE LOCATION_SEQ;

DROP TABLE REF_CONDITION;

DROP TABLE REF_HARDWARE_TYPE;

DROP TABLE REF_PERMISSION;
DROP SEQUENCE REF_PERMISSION_SEQ;

DROP TABLE REF_PERMISSION_TO_ROLE;
DROP SEQUENCE REF_PERMISSION_TO_ROLE_SEQ;

DROP TABLE ROLE;
DROP SEQUENCE ROLE_SEQ;

DROP TABLE ROLE_TO_USER;
DROP SEQUENCE ROLE_TO_USER_SEQ;

DROP TABLE SMTP;
DROP SEQUENCE SMTP_SEQ;

DROP TABLE SOFTWARE;
DROP SEQUENCE SOFTWARE_SEQ;

DROP TABLE TEAM;
DROP SEQUENCE TEAM_SEQ;

DROP TABLE TEAM_TO_USER;
DROP SEQUENCE TEAM_TO_USER_SEQ;

DROP TABLE USER;
DROP SEQUENCE USER_SEQ;

ALTER TABLE ACCESS_TOKEN DROP CONSTRAINT FK_ACCESS_TOKEN_USER_KEY;
ALTER TABLE EVENT DROP CONSTRAINT FK_EVENT_CREATED_BY;
ALTER TABLE EVENT DROP CONSTRAINT FK_EVENT_OWNER_ID;
ALTER TABLE EVENT DROP CONSTRAINT FK_EVENT_HARDWARE_ID;
ALTER TABLE EVENT DROP CONSTRAINT FK_EVENT_SOFTWARE_ID;
ALTER TABLE EVENT_TO_LOCATION DROP CONSTRAINT FK_EVENT_TO_LOCATION_EVENT_ID;
ALTER TABLE EVENT_TO_LOCATION DROP CONSTRAINT FK_EVENT_TO_LOCATION_LOCATION_ID;
ALTER TABLE EVENT_TO_USER DROP CONSTRAINT FK_EVENT_TO_USER_EVENT_ID;
ALTER TABLE EVENT_TO_USER DROP CONSTRAINT FK_EVENT_TO_USER_USER_ID;
ALTER TABLE HARDWARE DROP CONSTRAINT FK_HARDWARE_LOCATION_ID;
ALTER TABLE HARDWARE DROP CONSTRAINT FK_HARDWARE_USER_ID;
ALTER TABLE HARDWARE DROP CONSTRAINT FK_HARDWARE_CONDITION;
ALTER TABLE HARDWARE_TO_SOFTWARE DROP CONSTRAINT FK_HARDWARE_TO_SOFTWARE_HARDWARE_ID;
ALTER TABLE HARDWARE_TO_SOFTWARE DROP CONSTRAINT FK_HARDWARE_TO_SOFTWARE_SOFTWARE_ID;
ALTER TABLE LOCATION DROP CONSTRAINT FK_LOCATION_ADDRESS_ID;
ALTER TABLE REF_PERMISSION_TO_ROLE DROP CONSTRAINT FK_REF_PERMISSION_TO_ROLE_PERMISSION_ID;
ALTER TABLE REF_PERMISSION_TO_ROLE DROP CONSTRAINT FK_REF_PERMISSION_TO_ROLE_ROLE_ID;
ALTER TABLE ROLE_TO_USER DROP CONSTRAINT FK_ROLE_TO_USER_ROLE_ID;
ALTER TABLE ROLE_TO_USER DROP CONSTRAINT FK_ROLE_TO_USER_USER_ID;
ALTER TABLE TEAM_TO_USER DROP CONSTRAINT FK_TEAM_TO_USER_USER_ID;
ALTER TABLE TEAM_TO_USER DROP CONSTRAINT FK_TEAM_TO_USER_TEAM_ID;
ALTER TABLE USER DROP CONSTRAINT FK_USER_ROLE_ID;


CREATE TABLE ACCESS_TOKEN (
    USER_KEY           NUMBER(10) NOT NULL NOT NULL,
    TOKEN              VARCHAR2(200) NOT NULL,
    EXPIRATION         DATE NOT NULL,
    CONSTRAINT ACCESS_TOKEN_PK PRIMARY KEY ( USER_KEY ));

CREATE SEQUENCE ACCESS_TOKEN_SEQ;


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
    DATE_SPECIFIED     DATE,
    DESCRIPTION        VARCHAR2(2000),
    CREATED_BY         NUMBER(10),
    OWNER_ID           NUMBER(10),
    HARDWARE_ID        NUMBER(10),
    SOFTWARE_ID        NUMBER(10),
    CONSTRAINT EVENT_PK PRIMARY KEY ( KEY ));

CREATE SEQUENCE EVENT_SEQ;


CREATE TABLE EVENT_TO_LOCATION (
    KEY                NUMBER(10) NOT NULL,
    EVENT_ID           NUMBER(10) NOT NULL,
    LOCATION_ID        NUMBER(10) NOT NULL,
    CONSTRAINT EVENT_TO_LOCATION_PK PRIMARY KEY ( KEY ));

CREATE SEQUENCE EVENT_TO_LOCATION_SEQ;


CREATE TABLE EVENT_TO_USER (
    KEY                NUMBER(10) NOT NULL,
    EVENT_ID           NUMBER(10) NOT NULL,
    USER_ID            NUMBER(10) NOT NULL,
    CONSTRAINT EVENT_TO_USER_PK PRIMARY KEY ( KEY ));

CREATE SEQUENCE EVENT_TO_USER_SEQ;


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

ALTER TABLE ACCESS_TOKEN ADD CONSTRAINT FK_ACCESS_TOKEN_USER_KEY FOREIGN KEY (USER_KEY) REFERENCES USER(KEY);
ALTER TABLE EVENT ADD CONSTRAINT FK_EVENT_CREATED_BY FOREIGN KEY (CREATED_BY) REFERENCES USER(KEY);
ALTER TABLE EVENT ADD CONSTRAINT FK_EVENT_OWNER_ID FOREIGN KEY (OWNER_ID) REFERENCES USER(KEY);
ALTER TABLE EVENT ADD CONSTRAINT FK_EVENT_HARDWARE_ID FOREIGN KEY (HARDWARE_ID) REFERENCES HARDWARE(KEY);
ALTER TABLE EVENT ADD CONSTRAINT FK_EVENT_SOFTWARE_ID FOREIGN KEY (SOFTWARE_ID) REFERENCES SOFTWARE(KEY);
ALTER TABLE EVENT_TO_LOCATION ADD CONSTRAINT FK_EVENT_TO_LOCATION_EVENT_ID FOREIGN KEY (EVENT_ID) REFERENCES EVENT(KEY);
ALTER TABLE EVENT_TO_LOCATION ADD CONSTRAINT FK_EVENT_TO_LOCATION_LOCATION_ID FOREIGN KEY (LOCATION_ID) REFERENCES USER(KEY);
ALTER TABLE EVENT_TO_USER ADD CONSTRAINT FK_EVENT_TO_USER_EVENT_ID FOREIGN KEY (EVENT_ID) REFERENCES EVENT(KEY);
ALTER TABLE EVENT_TO_USER ADD CONSTRAINT FK_EVENT_TO_USER_USER_ID FOREIGN KEY (USER_ID) REFERENCES USER(KEY);
ALTER TABLE HARDWARE ADD CONSTRAINT FK_HARDWARE_LOCATION_ID FOREIGN KEY (LOCATION_ID) REFERENCES LOCATION(KEY);
ALTER TABLE HARDWARE ADD CONSTRAINT FK_HARDWARE_USER_ID FOREIGN KEY (USER_ID) REFERENCES USER(KEY);
ALTER TABLE HARDWARE ADD CONSTRAINT FK_HARDWARE_CONDITION FOREIGN KEY (CONDITION) REFERENCES REF_CONDITION(CODE);
ALTER TABLE HARDWARE_TO_SOFTWARE ADD CONSTRAINT FK_HARDWARE_TO_SOFTWARE_HARDWARE_ID FOREIGN KEY (HARDWARE_ID) REFERENCES HARDWARE(KEY);
ALTER TABLE HARDWARE_TO_SOFTWARE ADD CONSTRAINT FK_HARDWARE_TO_SOFTWARE_SOFTWARE_ID FOREIGN KEY (SOFTWARE_ID) REFERENCES SOFTWARE(KEY);
ALTER TABLE LOCATION ADD CONSTRAINT FK_LOCATION_ADDRESS_ID FOREIGN KEY (ADDRESS_ID) REFERENCES ADDRESS(KEY);
ALTER TABLE REF_PERMISSION_TO_ROLE ADD CONSTRAINT FK_REF_PERMISSION_TO_ROLE_PERMISSION_ID FOREIGN KEY (PERMISSION_ID) REFERENCES REF_PERMISSION(KEY);
ALTER TABLE REF_PERMISSION_TO_ROLE ADD CONSTRAINT FK_REF_PERMISSION_TO_ROLE_ROLE_ID FOREIGN KEY (ROLE_ID) REFERENCES ROLE(KEY);
ALTER TABLE ROLE_TO_USER ADD CONSTRAINT FK_ROLE_TO_USER_ROLE_ID FOREIGN KEY (ROLE_ID) REFERENCES ROLE(KEY);
ALTER TABLE ROLE_TO_USER ADD CONSTRAINT FK_ROLE_TO_USER_USER_ID FOREIGN KEY (USER_ID) REFERENCES USER(KEY);
ALTER TABLE TEAM_TO_USER ADD CONSTRAINT FK_TEAM_TO_USER_USER_ID FOREIGN KEY (USER_ID) REFERENCES USER(KEY);
ALTER TABLE TEAM_TO_USER ADD CONSTRAINT FK_TEAM_TO_USER_TEAM_ID FOREIGN KEY (TEAM_ID) REFERENCES TEAM(KEY);
ALTER TABLE USER ADD CONSTRAINT FK_USER_ROLE_ID FOREIGN KEY (ROLE_ID) REFERENCES ROLE(KEY);

-- PROTECTED CODE -->

-- REF_PERMISSION KEY CONVENTION :
-- First digit denotes label (1 = Hardware, 2 = Software, etc.)
-- Second digit denotes action (0 = create, 1 = read, 2 = update, 3 = delete)

INSERT INTO REF_PERMISSION VALUES (10,'createHardware', 'Create Hardware');
INSERT INTO REF_PERMISSION VALUES (11,'readHardware', 'Read Hardware');
INSERT INTO REF_PERMISSION VALUES (12,'updateHardware', 'Edit Hardware');
INSERT INTO REF_PERMISSION VALUES (13,'deleteHardware', 'Delete Hardware');

INSERT INTO REF_PERMISSION VALUES (20,'createSoftware', 'Create Software');
INSERT INTO REF_PERMISSION VALUES (21,'readSoftware', 'Read Software');
INSERT INTO REF_PERMISSION VALUES (22,'updateSoftware', 'Edit Software');
INSERT INTO REF_PERMISSION VALUES (23,'deleteSoftware', 'Delete Software');

INSERT INTO REF_PERMISSION VALUES (30,'createBarcode', 'Create Barcode');
INSERT INTO REF_PERMISSION VALUES (31,'readBarcode', 'Read Barcode');
INSERT INTO REF_PERMISSION VALUES (33,'deleteBarcode', 'Delete Barcode');

INSERT INTO REF_PERMISSION VALUES (40,'createUser', 'Create User');
INSERT INTO REF_PERMISSION VALUES (41,'readUser', 'Read User');
INSERT INTO REF_PERMISSION VALUES (42,'updateUser', 'Edit User');
INSERT INTO REF_PERMISSION VALUES (43,'deleteUser', 'Delete User');

-- read/update/delete for user to enact on self, i.e. their own user but no others
INSERT INTO REF_PERMISSION(KEY, CODE, DESCRIPTION) VALUES (51, 'readSelf', 'Read Self');
INSERT INTO REF_PERMISSION(KEY, CODE, DESCRIPTION) VALUES (52, 'updateSelf', 'Edit Self');
-- INSERT INTO REF_PERMISSION(KEY, CODE, DESCRIPTION) VALUES (53, 'deleteSelf', 'deleteSelf');

INSERT INTO REF_PERMISSION VALUES (60,'createLocation', 'Create Location');
INSERT INTO REF_PERMISSION VALUES (61,'readLocation', 'Read Location');
INSERT INTO REF_PERMISSION VALUES (62,'updateLocation', 'Edit Location');
INSERT INTO REF_PERMISSION VALUES (63,'deleteLocation', 'Delete Location');

INSERT INTO REF_PERMISSION VALUES (70,'createAddress', 'Create Address');
INSERT INTO REF_PERMISSION VALUES (71,'readAddress', 'Read Address');
INSERT INTO REF_PERMISSION VALUES (72,'updateAddress', 'Edit Address');
INSERT INTO REF_PERMISSION VALUES (73,'deleteAddress', 'Delete Address');

INSERT INTO REF_PERMISSION VALUES (80,'createEvent', 'Create Event');
INSERT INTO REF_PERMISSION VALUES (81,'readEvent', 'Read Event');
INSERT INTO REF_PERMISSION VALUES (82,'updateEvent', 'Edit Event');
INSERT INTO REF_PERMISSION VALUES (83,'deleteEvent', 'Delete Event');

INSERT INTO ROLE VALUES (1,'admin');
INSERT INTO ROLE VALUES (2,'inventoryManager');
INSERT INTO ROLE VALUES (3,'basicUser');

-- Add all permissions to admin role --
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (1,10,1);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (2,11,1);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (3,12,1);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (4,13,1);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (5,20,1);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (6,21,1);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (7,22,1);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (8,23,1);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (9,30,1);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (10,31,1);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (11,33,1);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (12,40,1);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (13,41,1);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (14,42,1);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (15,43,1);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (33,51,1);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (34,52,1);
-- INSERT INTO REF_PERMISSION_TO_ROLE VALUES (35,53,1);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (38,60,1);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (39,61,1);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (40,62,1);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (41,63,1);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (42,70,1);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (43,71,1);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (44,72,1);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (45,73,1);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (54,80,1);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (55,81,1);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (56,82,1);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (57,83,1);

-- Add permissions to inventory manager role -- 
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (16,10,2);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (17,11,2);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (18,12,2);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (19,13,2);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (20,20,2);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (21,21,2);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (22,22,2);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (23,23,2);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (24,30,2);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (25,31,2);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (26,33,2);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (27,41,2);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (28,42,2);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (36,51,2);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (46,60,2);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (47,61,2);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (48,62,2);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (49,63,2);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (50,70,2);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (51,71,2);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (52,72,2);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (53,73,2);

-- Add permissions to basic user role --
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (29,11,3);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (30,21,3);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (31,41,3);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (32,42,3);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (37,51,3);


-- Adds user, Username:'username1' Password:'password' --
INSERT INTO USER VALUES ( 10, 'username1', 'SHA-256$20000$UTF-8$uegf5hijubj9samgsbd4klhguc$014cfb0d05b9504a7d6e51a4ecc0480d4e0e9c9642feb6738142b3b1c960947a', 'first', 'last','mail@mail.com', '5555559999', '5555557777', true, 1);
INSERT INTO USER VALUES ( 11, 'nicholas.phillpott', 'SHA-256$20000$UTF-8$uegf5hijubj9samgsbd4klhguc$014cfb0d05b9504a7d6e51a4ecc0480d4e0e9c9642feb6738142b3b1c960947a', 'Nicholas', 'Phillpott','nicholas.phillpott@simoncomputing.com', '7575556765', '7575556543', true, 2);
INSERT INTO USER VALUES ( 14, 'elizabeth.villaflor', 'SHA-256$20000$UTF-8$uegf5hijubj9samgsbd4klhguc$014cfb0d05b9504a7d6e51a4ecc0480d4e0e9c9642feb6738142b3b1c960947a', 'first', 'last','email@email.com', '5555559999', '5555557777', true, 3);
INSERT INTO USER Values(123, 'bjg3wk', 'googleUser', 'Brendan', 'Goggin', 'bjg3wk@virginia.edu', '1234567890', '1234567890', true, 1);
INSERT INTO USER Values(1234, 'nickwp54', 'googleUser', 'Nicholas', 'Phillpott', 'nickwp54@vt.edu', '1234567890', '1234567890', true, 1);

INSERT INTO REF_CONDITION  VALUES ('good', 'good');

INSERT INTO GOOGLE_CLIENT VALUES (1,  'id', 'secret');


INSERT INTO SMTP Values(1, 'smtp.host.com', 465, 'username', 'password', true);