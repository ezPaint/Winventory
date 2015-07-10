ALTER TABLE ACCESS_TOKEN ADD CONSTRAINT FK_ACCESS_TOKEN_USER_KEY FOREIGN KEY (USER_KEY) REFERENCES USER(KEY);
ALTER TABLE EVENT_TO_HARDWARE ADD CONSTRAINT FK_EVENT_TO_HARDWARE_EVENT_ID FOREIGN KEY (EVENT_ID) REFERENCES EVENT(KEY);
ALTER TABLE EVENT_TO_HARDWARE ADD CONSTRAINT FK_EVENT_TO_HARDWARE_HARDWARE_ID FOREIGN KEY (HARDWARE_ID) REFERENCES HARDWARE(KEY);
ALTER TABLE EVENT_TO_LOCATION ADD CONSTRAINT FK_EVENT_TO_LOCATION_EVENT_ID FOREIGN KEY (EVENT_ID) REFERENCES EVENT(KEY);
ALTER TABLE EVENT_TO_LOCATION ADD CONSTRAINT FK_EVENT_TO_LOCATION_LOCATION_ID FOREIGN KEY (LOCATION_ID) REFERENCES LOCATION(KEY);
ALTER TABLE EVENT_TO_SOFTWARE ADD CONSTRAINT FK_EVENT_TO_SOFTWARE_EVENT_ID FOREIGN KEY (EVENT_ID) REFERENCES EVENT(KEY);
ALTER TABLE EVENT_TO_SOFTWARE ADD CONSTRAINT FK_EVENT_TO_SOFTWARE_SOFTWARE_ID FOREIGN KEY (SOFTWARE_ID) REFERENCES SOFTWARE(KEY);
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

INSERT INTO REF_PERMISSION VALUES (10,'createHardware', 'createHardware');
INSERT INTO REF_PERMISSION VALUES (11,'readHardware', 'readHardware');
INSERT INTO REF_PERMISSION VALUES (12,'updateHardware', 'updateHardware');
INSERT INTO REF_PERMISSION VALUES (13,'deleteHardware', 'deleteHardware');

INSERT INTO REF_PERMISSION VALUES (20,'createSoftware', 'createSoftware');
INSERT INTO REF_PERMISSION VALUES (21,'readSoftware', 'readSoftware');
INSERT INTO REF_PERMISSION VALUES (22,'updateSoftware', 'updateSoftware');
INSERT INTO REF_PERMISSION VALUES (23,'deleteSoftware', 'deleteSoftware');

INSERT INTO REF_PERMISSION VALUES (30,'createBarcode', 'createBarcode');
INSERT INTO REF_PERMISSION VALUES (31,'readBarcode', 'readBarcode');
INSERT INTO REF_PERMISSION VALUES (33,'deleteBarcode', 'deleteBarcode');

INSERT INTO REF_PERMISSION VALUES (40,'createUser', 'createUser');
INSERT INTO REF_PERMISSION VALUES (41,'readUser', 'readUser');
INSERT INTO REF_PERMISSION VALUES (42,'updateUser', 'updateUser');
INSERT INTO REF_PERMISSION VALUES (43,'deleteUser', 'deleteUser');


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

-- Add permissions to basic user role --
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (29,11,3);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (30,21,3);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (31,41,3);
INSERT INTO REF_PERMISSION_TO_ROLE VALUES (32,42,3);

-- Adds user, Username:'username1' Password:'password' --
INSERT INTO USER VALUES ( 10, 'username1', 'SHA-256$20000$UTF-8$uegf5hijubj9samgsbd4klhguc$014cfb0d05b9504a7d6e51a4ecc0480d4e0e9c9642feb6738142b3b1c960947a', 'first', 'last','mail@mail.com', '5555559999', '5555557777', true, 1);
INSERT INTO USER VALUES ( 11, 'nicholas.phillpott', 'SHA-256$20000$UTF-8$uegf5hijubj9samgsbd4klhguc$014cfb0d05b9504a7d6e51a4ecc0480d4e0e9c9642feb6738142b3b1c960947a', 'Nicholas', 'Phillpott','nicholas.phillpott@simoncomputing.com', '7575556765', '7575556543', true, 2);
INSERT INTO USER VALUES ( 14, 'elizabeth.villaflor', 'SHA-256$20000$UTF-8$uegf5hijubj9samgsbd4klhguc$014cfb0d05b9504a7d6e51a4ecc0480d4e0e9c9642feb6738142b3b1c960947a', 'first', 'last','mail@mail.com', '5555559999', '5555557777', true, 3);

INSERT INTO REF_CONDITION  VALUES ('good', 'good');

INSERT INTO GOOGLE_CLIENT VALUES (1,  'id', 'secret');

-- Adds google login info to USER --
INSERT INTO USER Values(123, 'bjg3wk', 'googleUser', 'Brendan', 'Goggin', 'bjg3wk@virginia.edu', '1234567890', '1234567890', true, 1);
INSERT INTO USER Values(1234, 'nickwp54', 'googleUser', 'Nicholas', 'Phillpott', 'nickwp54@vt.edu', '1234567890', '1234567890', true, 1);


INSERT INTO SMTP Values(1, 'smtp.host.com', 465, 'username', 'password', true);