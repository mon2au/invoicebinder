-- user
INSERT INTO user(firstname, lastname, password, userStatus, username, primaryEmailId, roleId) VALUES ('_FIRSTNAME_','_LASTNAME_','_PASSWORD_','_USERSTATUS_','_USERNAME_', (SELECT id from email WHERE emailAddress = '_EMAILADDRESS_'), 1);
