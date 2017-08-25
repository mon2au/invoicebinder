-- role
INSERT INTO `role` (`id`, `name`) VALUES (1, 'SYSTEM');
INSERT INTO `role` (`id`, `name`) VALUES (2, 'ADMIN');
INSERT INTO `role` (`id`, `name`) VALUES (3, 'USER');

-- email
INSERT INTO `email` (`emailAddress`) VALUES ('invoicebinder@gmail.com');

-- user
INSERT INTO `user` (`id`, `firstname`, `lastname`, `logintimestamp`, `password`, `userStatus`, `username`, `primaryEmailId`, `roleId`) VALUES (1, 'Demo', 'Person', '2013-05-10 10:00:00', 'password', 'ACTIVE', 'demo', (SELECT id from email WHERE emailAddress = 'invoicebinder@gmail.com'), 1);
