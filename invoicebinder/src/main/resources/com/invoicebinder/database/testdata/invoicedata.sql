-- invoices
INSERT INTO `invoice` (`amount`, `description`, `dueDate`, `invoiceDate`, `paymentDate`, `invoiceNumber`, `invoiceStatus`, `purchOrdNumber`, `type`, `clientId`) VALUES ('230.84', 'thank you for your business', '2017-01-01', '2017-01-01', '2017-01-02', 'INV1', 'DRAFT', 'PO2121', 'S', 1);
INSERT INTO `item` (`Qty`, `description`, `name`, `price`) VALUES (1, 'Living Doll', 'Living Doll', '49.95');
INSERT INTO `item` (`Qty`, `description`, `name`, `price`) VALUES (1, 'Sunny Girl', 'Sunny Girl', '59.95');
INSERT INTO `item` (`Qty`, `description`, `name`, `price`) VALUES (1, 'Espirit', 'Espirit', '99.95');
INSERT INTO `invoiceitem` (`invoice_id`, `item_id`) VALUES (1, 1);
INSERT INTO `invoiceitem` (`invoice_id`, `item_id`) VALUES (1, 2);
INSERT INTO `invoiceitem` (`invoice_id`, `item_id`) VALUES (1, 3);

INSERT INTO `invoice` (`amount`, `description`, `dueDate`, `invoiceDate`, `paymentDate`, `invoiceNumber`, `invoiceStatus`, `purchOrdNumber`, `type`, `clientId`) VALUES ('252.89', 'thank you for your business', '2017-01-10', '2017-01-01', '2017-01-10', 'INV2', 'PAID', 'PO2122', 'S', 2);
INSERT INTO `item` (`Qty`, `description`, `name`, `price`) VALUES (1, 'Espirit', 'Espirit', '99.95');
INSERT INTO `item` (`Qty`, `description`, `name`, `price`) VALUES (1, 'Martini', 'Martini', '129.95');
INSERT INTO `invoiceitem` (`invoice_id`, `item_id`) VALUES (2, 4);
INSERT INTO `invoiceitem` (`invoice_id`, `item_id`) VALUES (2, 5);

INSERT INTO `invoice` (`amount`, `description`, `dueDate`, `invoiceDate`, `paymentDate`, `invoiceNumber`, `invoiceStatus`, `purchOrdNumber`, `type`, `clientId`) VALUES ('241.84', 'thank you for your business', '2017-01-01', '2017-01-01', '2017-01-02', 'INV3', 'PAID', 'PO2123', 'S', 3);
INSERT INTO `item` (`Qty`, `description`, `name`, `price`) VALUES (1, 'Espirit', 'Espirit', '99.95');
INSERT INTO `item` (`Qty`, `description`, `name`, `price`) VALUES (1, 'Firefly', 'Firefly', '59.95');
INSERT INTO `item` (`Qty`, `description`, `name`, `price`) VALUES (1, 'Sunny Girl', 'Sunny Girl', '59.95');
INSERT INTO `invoiceitem` (`invoice_id`, `item_id`) VALUES (3, 6);
INSERT INTO `invoiceitem` (`invoice_id`, `item_id`) VALUES (3, 7);
INSERT INTO `invoiceitem` (`invoice_id`, `item_id`) VALUES (3, 8);

INSERT INTO `invoice` (`amount`, `description`, `dueDate`, `invoiceDate`, `paymentDate`, `invoiceNumber`, `invoiceStatus`, `purchOrdNumber`, `type`, `clientId`) VALUES ('417.78', 'thank you for your business', '2016-12-10', '2016-12-01', '2016-12-12', 'INV4', 'PAID', 'PO2124', 'S', 4);
INSERT INTO `item` (`Qty`, `description`, `name`, `price`) VALUES (2, 'Espirit', 'Espirit', '99.95');
INSERT INTO `item` (`Qty`, `description`, `name`, `price`) VALUES (1, 'Martini', 'Martini', '129.95');
INSERT INTO `item` (`Qty`, `description`, `name`, `price`) VALUES (1, 'Living Doll', 'Living Doll', '49.95');
INSERT INTO `invoiceitem` (`invoice_id`, `item_id`) VALUES (4, 9);
INSERT INTO `invoiceitem` (`invoice_id`, `item_id`) VALUES (4, 10);
INSERT INTO `invoiceitem` (`invoice_id`, `item_id`) VALUES (4, 11);

INSERT INTO `invoice` (`amount`, `description`, `dueDate`, `invoiceDate`, `paymentDate`, `invoiceNumber`, `invoiceStatus`, `purchOrdNumber`, `type`, `clientId`) VALUES ('54.95', 'thank you for your business', '2016-12-10', '2016-12-01', '2016-12-10', 'INV5', 'PAID', 'PO2125', 'S', 5);
INSERT INTO `item` (`Qty`, `description`, `name`, `price`) VALUES (1, 'Living Doll', 'Living Doll', '49.95');
INSERT INTO `invoiceitem` (`invoice_id`, `item_id`) VALUES (5, 12);

INSERT INTO `invoice` (`amount`, `description`, `dueDate`, `invoiceDate`, `paymentDate`, `invoiceNumber`, `invoiceStatus`, `purchOrdNumber`, `type`, `clientId`) VALUES ('417.78', 'thank you for your business', '2016-11-10', '2016-11-10', '2016-11-12', 'INV6', 'PAID', 'PO2126', 'S', 6);
INSERT INTO `item` (`Qty`, `description`, `name`, `price`) VALUES (2, 'Espirit', 'Espirit', '99.95');
INSERT INTO `item` (`Qty`, `description`, `name`, `price`) VALUES (1, 'Martini', 'Martini', '129.95');
INSERT INTO `item` (`Qty`, `description`, `name`, `price`) VALUES (1, 'Living Doll', 'Living Doll', '49.95');
INSERT INTO `invoiceitem` (`invoice_id`, `item_id`) VALUES (6, 13);
INSERT INTO `invoiceitem` (`invoice_id`, `item_id`) VALUES (6, 14);
INSERT INTO `invoiceitem` (`invoice_id`, `item_id`) VALUES (6, 15);
 
INSERT INTO `invoice` (`amount`, `description`, `dueDate`, `invoiceDate`, `paymentDate`, `invoiceNumber`, `invoiceStatus`, `purchOrdNumber`, `type`, `clientId`) VALUES ('241.84', 'thank you for your business', '2017-02-10', '2017-02-10', '2017-02-10', 'INV7', 'PAID', 'PO2127', 'S', 7);
INSERT INTO `item` (`Qty`, `description`, `name`, `price`) VALUES (1, 'Espirit', 'Espirit', '99.95');
INSERT INTO `item` (`Qty`, `description`, `name`, `price`) VALUES (1, 'Firefly', 'Firefly', '59.95');
INSERT INTO `item` (`Qty`, `description`, `name`, `price`) VALUES (1, 'Sunny Girl', 'Sunny Girl', '59.95');
INSERT INTO `invoiceitem` (`invoice_id`, `item_id`) VALUES (7, 16);
INSERT INTO `invoiceitem` (`invoice_id`, `item_id`) VALUES (7, 17);
INSERT INTO `invoiceitem` (`invoice_id`, `item_id`) VALUES (7, 18);

INSERT INTO `invoice` (`amount`, `description`, `dueDate`, `invoiceDate`, `paymentDate`, `invoiceNumber`, `invoiceStatus`, `purchOrdNumber`, `type`, `clientId`) VALUES ('54.95', 'thank you for your business', '2017-04-10', '2017-04-10', '2017-04-11', 'INV8', 'PAID', 'PO2128', 'S', 8);
INSERT INTO `item` (`Qty`, `description`, `name`, `price`) VALUES (1, 'Living Doll', 'Living Doll', '49.95');
INSERT INTO `invoiceitem` (`invoice_id`, `item_id`) VALUES (8, 19);

INSERT INTO `invoice` (`amount`, `description`, `dueDate`, `invoiceDate`, `paymentDate`, `invoiceNumber`, `invoiceStatus`, `purchOrdNumber`, `type`, `clientId`) VALUES ('252.89', 'thank you for your business', '2017-06-10', '2017-06-11', '2017-06-14', 'INV9', 'PAID', 'PO2129', 'S', 2);
INSERT INTO `item` (`Qty`, `description`, `name`, `price`) VALUES (1, 'Espirit', 'Espirit', '99.95');
INSERT INTO `item` (`Qty`, `description`, `name`, `price`) VALUES (1, 'Martini', 'Martini', '129.95');
INSERT INTO `invoiceitem` (`invoice_id`, `item_id`) VALUES (9, 20);
INSERT INTO `invoiceitem` (`invoice_id`, `item_id`) VALUES (9, 21);
 
INSERT INTO `invoice` (`amount`, `description`, `dueDate`, `invoiceDate`, `paymentDate`, `invoiceNumber`, `invoiceStatus`, `purchOrdNumber`, `type`, `clientId`) VALUES ('252.89', 'thank you for your business', '2017-02-10', '2017-03-01', '2017-03-01', 'INV10', 'DRAFT', 'PO2130', 'S', 2);
INSERT INTO `item` (`Qty`, `description`, `name`, `price`) VALUES (1, 'Espirit', 'Espirit', '99.95');
INSERT INTO `item` (`Qty`, `description`, `name`, `price`) VALUES (1, 'Martini', 'Martini', '129.95');
INSERT INTO `invoiceitem` (`invoice_id`, `item_id`) VALUES (10, 22);
INSERT INTO `invoiceitem` (`invoice_id`, `item_id`) VALUES (10, 23);

INSERT INTO `invoice` (`amount`, `description`, `dueDate`, `invoiceDate`, `paymentDate`, `invoiceNumber`, `invoiceStatus`, `purchOrdNumber`, `type`, `clientId`) VALUES ('252.89', 'thank you for your business', '2017-06-28', '2017-06-28', '2017-06-29', 'INV11', 'PAID', 'PO2131', 'S', 2);
INSERT INTO `item` (`Qty`, `description`, `name`, `price`) VALUES (1, 'Espirit', 'Espirit', '99.95');
INSERT INTO `item` (`Qty`, `description`, `name`, `price`) VALUES (1, 'Martini', 'Martini', '129.95');
INSERT INTO `invoiceitem` (`invoice_id`, `item_id`) VALUES (11, 24);
INSERT INTO `invoiceitem` (`invoice_id`, `item_id`) VALUES (11, 25);

INSERT INTO `invoice` (`amount`, `description`, `dueDate`, `invoiceDate`, `paymentDate`, `invoiceNumber`, `invoiceStatus`, `purchOrdNumber`, `type`, `clientId`) VALUES ('242.89', 'thank you for your business', '2017-06-27', '2017-06-27', '2017-07-27', 'INV12', 'DRAFT', 'PO2132', 'S', 2);
INSERT INTO `item` (`Qty`, `description`, `name`, `price`) VALUES (1, 'Espirit', 'Espirit', '99.95');
INSERT INTO `item` (`Qty`, `description`, `name`, `price`) VALUES (1, 'Martini', 'Martini', '129.95');
INSERT INTO `invoiceitem` (`invoice_id`, `item_id`) VALUES (12, 26);
INSERT INTO `invoiceitem` (`invoice_id`, `item_id`) VALUES (12, 27);

INSERT INTO `invoice` (`amount`, `description`, `dueDate`, `invoiceDate`, `paymentDate`, `invoiceNumber`, `invoiceStatus`, `purchOrdNumber`, `type`, `clientId`) VALUES ('252.89', 'thank you for your business', '2017-06-27', '2017-06-27', '2017-07-27', 'INV13', 'DRAFT', 'PO2133', 'S', 2);
INSERT INTO `item` (`Qty`, `description`, `name`, `price`) VALUES (1, 'Espirit', 'Espirit', '99.95');
INSERT INTO `item` (`Qty`, `description`, `name`, `price`) VALUES (1, 'Martini', 'Martini', '129.95');
INSERT INTO `invoiceitem` (`invoice_id`, `item_id`) VALUES (13, 28);
INSERT INTO `invoiceitem` (`invoice_id`, `item_id`) VALUES (13, 29);

INSERT INTO `invoice` (`amount`, `description`, `dueDate`, `invoiceDate`, `paymentDate`, `invoiceNumber`, `invoiceStatus`, `purchOrdNumber`, `type`, `clientId`) VALUES ('252.89', 'thank you for your business', '2017-06-27', '2017-06-27', '2017-07-27', 'INV14', 'DRAFT', 'PO2134', 'S', 2);
INSERT INTO `item` (`Qty`, `description`, `name`, `price`) VALUES (1, 'Espirit', 'Espirit', '99.95');
INSERT INTO `item` (`Qty`, `description`, `name`, `price`) VALUES (1, 'Martini', 'Martini', '129.95');
INSERT INTO `invoiceitem` (`invoice_id`, `item_id`) VALUES (14, 30);
INSERT INTO `invoiceitem` (`invoice_id`, `item_id`) VALUES (14, 31);

INSERT INTO `invoice` (`amount`, `description`, `dueDate`, `invoiceDate`, `paymentDate`, `invoiceNumber`, `invoiceStatus`, `purchOrdNumber`, `type`, `clientId`) VALUES ('252.89', 'thank you for your business', '2017-02-27', '2017-02-27', '2017-02-27', 'INV15', 'DRAFT', 'PO2135', 'S', 2);
INSERT INTO `item` (`Qty`, `description`, `name`, `price`) VALUES (1, 'Espirit', 'Espirit', '99.95');
INSERT INTO `item` (`Qty`, `description`, `name`, `price`) VALUES (1, 'Martini', 'Martini', '129.95');
INSERT INTO `invoiceitem` (`invoice_id`, `item_id`) VALUES (15, 32);
INSERT INTO `invoiceitem` (`invoice_id`, `item_id`) VALUES (15, 33);

INSERT INTO `invoice` (`amount`, `description`, `dueDate`, `invoiceDate`, `paymentDate`, `invoiceNumber`, `invoiceStatus`, `purchOrdNumber`, `type`, `clientId`) VALUES ('417.78', 'thank you for your business', '2017-03-10', '2017-03-10', '2017-03-12', 'INV16', 'PAID', 'PO2136', 'S', 6);
INSERT INTO `item` (`Qty`, `description`, `name`, `price`) VALUES (2, 'Espirit', 'Espirit', '99.95');
INSERT INTO `item` (`Qty`, `description`, `name`, `price`) VALUES (1, 'Martini', 'Martini', '129.95');
INSERT INTO `item` (`Qty`, `description`, `name`, `price`) VALUES (1, 'Living Doll', 'Living Doll', '49.95');
INSERT INTO `invoiceitem` (`invoice_id`, `item_id`) VALUES (16, 34);
INSERT INTO `invoiceitem` (`invoice_id`, `item_id`) VALUES (16, 35);
INSERT INTO `invoiceitem` (`invoice_id`, `item_id`) VALUES (16, 36);

INSERT INTO `invoice` (`amount`, `description`, `dueDate`, `invoiceDate`, `paymentDate`, `invoiceNumber`, `invoiceStatus`, `purchOrdNumber`, `type`, `clientId`) VALUES ('417.78', 'thank you for your business', '2017-06-10', '2017-06-10', '2017-06-12', 'INV17', 'PAID', 'PO2137', 'S', 6);
INSERT INTO `item` (`Qty`, `description`, `name`, `price`) VALUES (2, 'Espirit', 'Espirit', '99.95');
INSERT INTO `item` (`Qty`, `description`, `name`, `price`) VALUES (1, 'Martini', 'Martini', '129.95');
INSERT INTO `item` (`Qty`, `description`, `name`, `price`) VALUES (1, 'Living Doll', 'Living Doll', '49.95');
INSERT INTO `invoiceitem` (`invoice_id`, `item_id`) VALUES (17, 37);
INSERT INTO `invoiceitem` (`invoice_id`, `item_id`) VALUES (17, 38);
INSERT INTO `invoiceitem` (`invoice_id`, `item_id`) VALUES (17, 39);

INSERT INTO `invoice` (`amount`, `description`, `dueDate`, `invoiceDate`, `paymentDate`, `invoiceNumber`, `invoiceStatus`, `purchOrdNumber`, `type`, `clientId`) VALUES ('417.78', 'thank you for your business', '2017-06-10', '2017-06-10', '2017-06-12', 'INV18', 'PAID', 'PO2138', 'S', 6);
INSERT INTO `item` (`Qty`, `description`, `name`, `price`) VALUES (2, 'Espirit', 'Espirit', '99.95');
INSERT INTO `item` (`Qty`, `description`, `name`, `price`) VALUES (1, 'Martini', 'Martini', '129.95');
INSERT INTO `item` (`Qty`, `description`, `name`, `price`) VALUES (1, 'Living Doll', 'Living Doll', '49.95');
INSERT INTO `invoiceitem` (`invoice_id`, `item_id`) VALUES (18, 40);
INSERT INTO `invoiceitem` (`invoice_id`, `item_id`) VALUES (18, 41);
INSERT INTO `invoiceitem` (`invoice_id`, `item_id`) VALUES (18, 42);

INSERT INTO `invoice` (`amount`, `description`, `dueDate`, `invoiceDate`, `paymentDate`, `invoiceNumber`, `invoiceStatus`, `purchOrdNumber`, `type`, `clientId`) VALUES ('417.78', 'thank you for your business', '2017-07-10', '2017-07-10', '2017-07-12', 'INV19', 'PAID', 'PO2139', 'S', 6);
INSERT INTO `item` (`Qty`, `description`, `name`, `price`) VALUES (2, 'Espirit', 'Espirit', '99.95');
INSERT INTO `item` (`Qty`, `description`, `name`, `price`) VALUES (1, 'Martini', 'Martini', '129.95');
INSERT INTO `item` (`Qty`, `description`, `name`, `price`) VALUES (1, 'Living Doll', 'Living Doll', '49.95');
INSERT INTO `invoiceitem` (`invoice_id`, `item_id`) VALUES (19, 43);
INSERT INTO `invoiceitem` (`invoice_id`, `item_id`) VALUES (19, 44);
INSERT INTO `invoiceitem` (`invoice_id`, `item_id`) VALUES (19, 45);

INSERT INTO `invoice` (`amount`, `description`, `dueDate`, `invoiceDate`, `paymentDate`, `invoiceNumber`, `invoiceStatus`, `purchOrdNumber`, `type`, `clientId`) VALUES ('417.78', 'thank you for your business', '2017-08-10', '2017-08-10', '2017-08-12', 'INV20', 'PAID', 'PO2140', 'S', 6);
INSERT INTO `item` (`Qty`, `description`, `name`, `price`) VALUES (2, 'Espirit', 'Espirit', '99.95');
INSERT INTO `item` (`Qty`, `description`, `name`, `price`) VALUES (1, 'Martini', 'Martini', '129.95');
INSERT INTO `item` (`Qty`, `description`, `name`, `price`) VALUES (1, 'Living Doll', 'Living Doll', '49.95');
INSERT INTO `invoiceitem` (`invoice_id`, `item_id`) VALUES (20, 46);
INSERT INTO `invoiceitem` (`invoice_id`, `item_id`) VALUES (20, 47);
INSERT INTO `invoiceitem` (`invoice_id`, `item_id`) VALUES (20, 48);

INSERT INTO `invoice` (`amount`, `description`, `dueDate`, `invoiceDate`, `paymentDate`, `invoiceNumber`, `invoiceStatus`, `purchOrdNumber`, `type`, `clientId`) VALUES ('417.78', 'thank you for your business', '2017-09-10', '2017-09-10', '2017-09-12', 'INV21', 'PAID', 'PO2141', 'S', 6);
INSERT INTO `item` (`Qty`, `description`, `name`, `price`) VALUES (2, 'Espirit', 'Espirit', '99.95');
INSERT INTO `item` (`Qty`, `description`, `name`, `price`) VALUES (1, 'Martini', 'Martini', '129.95');
INSERT INTO `item` (`Qty`, `description`, `name`, `price`) VALUES (1, 'Living Doll', 'Living Doll', '49.95');
INSERT INTO `invoiceitem` (`invoice_id`, `item_id`) VALUES (21, 49);
INSERT INTO `invoiceitem` (`invoice_id`, `item_id`) VALUES (21, 50);
INSERT INTO `invoiceitem` (`invoice_id`, `item_id`) VALUES (21, 51);
