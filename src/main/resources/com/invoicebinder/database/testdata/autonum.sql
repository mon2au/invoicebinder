-- autonum
-- should be done after invoice so that it can add the correct starting auto number.
INSERT INTO `autonum` (`id`, `numPrefix`, `numSuffix`, `numValue`) VALUES ('INVOICE', 'INV', '', (select count(*) + 1 from invoice));
INSERT INTO `autonum` (`id`, `numPrefix`, `numSuffix`, `numValue`) VALUES ('PAYMENT', 'REF', '', 10001);