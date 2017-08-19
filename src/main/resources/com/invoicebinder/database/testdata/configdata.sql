-- configuration settings for the application.
-- Business config
INSERT INTO `configuration` (`configKey`, `configSection`, `dataType`, `maxLength`, `value`) VALUES ('BUSINESSNAME', 'Business', 'String', 100, 'Demo Company');
INSERT INTO `configuration` (`configKey`, `configSection`, `dataType`, `maxLength`, `value`) VALUES ('BUSINESSABN', 'Business', 'String', 11, '53004085616');
INSERT INTO `configuration` (`configKey`, `configSection`, `dataType`, `maxLength`, `value`) VALUES ('BUSINESSADDRESS1', 'Business', 'String', 100, '101 Demo Place');
INSERT INTO `configuration` (`configKey`, `configSection`, `dataType`, `maxLength`, `value`) VALUES ('BUSINESSADDRESS2', 'Business', 'String', 100, '');
INSERT INTO `configuration` (`configKey`, `configSection`, `dataType`, `maxLength`, `value`) VALUES ('BUSINESSCITY', 'Business', 'String', 50, 'Sydney');
INSERT INTO `configuration` (`configKey`, `configSection`, `dataType`, `maxLength`, `value`) VALUES ('BUSINESSSTATE', 'Business', 'String', 50, 'NSW');
INSERT INTO `configuration` (`configKey`, `configSection`, `dataType`, `maxLength`, `value`) VALUES ('BUSINESSPOSTCODE', 'Business', 'String', 50, '2000');
INSERT INTO `configuration` (`configKey`, `configSection`, `dataType`, `maxLength`, `value`) VALUES ('BUSINESSCOUNTRYCODE', 'Business', 'String', 50, 'AU');
INSERT INTO `configuration` (`configKey`, `configSection`, `dataType`, `maxLength`, `value`) VALUES ('BUSINESSLOGOURL', 'Business', 'String', 50, '/images/logo.png');
INSERT INTO `configuration` (`configKey`, `configSection`, `dataType`, `maxLength`, `value`) VALUES ('BUSINESSPHONE', 'Business', 'String', 10, '0299998888');
INSERT INTO `configuration` (`configKey`, `configSection`, `dataType`, `maxLength`, `value`) VALUES ('BUSINESSFAX', 'Business', 'String', 10, '0299998888');
INSERT INTO `configuration` (`configKey`, `configSection`, `dataType`, `maxLength`, `value`) VALUES ('BUSINESSEMAIL', 'Business', 'String', 50, 'invoicebinder@gmail.com');
INSERT INTO `configuration` (`configKey`, `configSection`, `dataType`, `maxLength`, `value`) VALUES ('BUSINESSABNLABEL', 'Business', 'String', 50, 'ABN');

-- Email config
INSERT INTO `configuration` (`configKey`, `configSection`, `dataType`, `maxLength`, `value`) VALUES ('EMAILINVOICETEMPLATE', 'Email', 'String', 200, 'Dear [ClientName],\nPlease find the invoice [InvoiceNo] for amount [InvoiceAmount] attached.\nRegards,\n[BusinessName]');

-- SiteTemplates config
--INSERT INTO `configuration` (`configKey`, `configSection`, `dataType`, `maxLength`, `value`) VALUES ('SITETEMPLATEID', 'SiteTemplates', 'String', 50, 'Default');

-- site template data
--INSERT INTO `sitetemplatedata` (`TemplateID`, `dataKey`, `dataValue`) VALUES ('Default', 'TITLE', 'Prestige Car Detailing');
--INSERT INTO `sitetemplatedata` (`TemplateID`, `dataKey`, `dataValue`) VALUES ('Default', 'SLOGAN', 'Feel the magic of Prestige Car Detailing');

-- SocialMedia config
--INSERT INTO `configuration` (`configKey`, `configSection`, `dataType`, `maxLength`, `value`) VALUES ('FACEBOOKURL', 'SocialMedia', 'String', 50, 'http://www.facebook.com');
--INSERT INTO `configuration` (`configKey`, `configSection`, `dataType`, `maxLength`, `value`) VALUES ('TWITTERURL', 'SocialMedia', 'String', 50, 'http://www.twitter.com');
--INSERT INTO `configuration` (`configKey`, `configSection`, `dataType`, `maxLength`, `value`) VALUES ('GPLUSURL', 'SocialMedia', 'String', 50, 'http://www.google.com');

-- Application Settings
INSERT INTO `configuration` (`configKey`, `configSection`, `dataType`, `maxLength`, `value`) VALUES ('APPLANDINGPAGE', 'ApplicationSettings', 'String', 50, 'LOGINPAGE');
INSERT INTO `configuration` (`configKey`, `configSection`, `dataType`, `maxLength`, `value`) VALUES ('APPTITLE', 'ApplicationSettings', 'String', 50, 'Demo Company');
INSERT INTO `configuration` (`configKey`, `configSection`, `dataType`, `maxLength`, `value`) VALUES ('APPSLOGAN', 'ApplicationSettings', 'String', 50, 'Welcome To Demo Company');
INSERT INTO `configuration` (`configKey`, `configSection`, `dataType`, `maxLength`, `value`) VALUES ('CURRENCY', 'ApplicationSettings', 'String', 10, 'AUD');
INSERT INTO `configuration` (`configKey`, `configSection`, `dataType`, `maxLength`, `value`) VALUES ('TAXLABEL', 'ApplicationSettings', 'String', 10, 'TAX');
-- Invoice Template Config
INSERT INTO `configuration` (`configKey`, `configSection`, `dataType`, `maxLength`, `value`) VALUES ('INVOICETEMPLATENAME', 'InvoiceTemplates', 'String', 10, 'Standard');