ALTER TABLE ACCESS_TOKEN ADD CONSTRAINT FK_ACCESS_TOKEN_USER_KEY FOREIGN KEY (USER_KEY) REFERENCES USER(KEY);
ALTER TABLE EVENT ADD CONSTRAINT FK_EVENT_CREATOR_ID FOREIGN KEY (CREATOR_ID) REFERENCES USER(KEY);
ALTER TABLE EVENT ADD CONSTRAINT FK_EVENT_USER_ID FOREIGN KEY (USER_ID) REFERENCES USER(KEY);
ALTER TABLE EVENT ADD CONSTRAINT FK_EVENT_HARDWARE_ID FOREIGN KEY (HARDWARE_ID) REFERENCES HARDWARE(KEY);
ALTER TABLE EVENT ADD CONSTRAINT FK_EVENT_SOFTWARE_ID FOREIGN KEY (SOFTWARE_ID) REFERENCES SOFTWARE(KEY);
ALTER TABLE EVENT ADD CONSTRAINT FK_EVENT_location_ID FOREIGN KEY (location_ID) REFERENCES LOCATION(KEY);
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
--ALTER TABLE EVENT_TO_HARDWARE ADD CONSTRAINT FK_EVENT_TO_HARDWARE FOREIGN KEY (HARDWARE_ID) REFERENCES HARDWARE(KEY); --
--ALTER TABLE EVENT_TO_SOFTWARE ADD CONSTRAINT FK_EVENT_TO_SOFTWARE FOREIGN KEY (SOFTWARE_ID) REFERENCES SOFTWARE(KEY); --
ALTER TABLE EVENT ADD CONSTRAINT FK_EVENT_CREATOR_ID FOREIGN KEY (CREATOR_ID) REFERENCES USER(KEY);

ALTER TABLE USER ADD UNIQUE(username);
ALTER TABLE USER ADD UNIQUE(email);

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