-- config
INSERT INTO configuration (configKey, configSection, dataType, maxLength, value) VALUES ('APPLANDINGPAGE', 'ApplicationSettings', 'String', 50, '_APPLANDINGPAGE_');
INSERT INTO configuration (configKey, configSection, dataType, maxLength, value) VALUES ('BUSINESSNAME', 'Business', 'String', 100, '_BUSINESSNAME_');
INSERT INTO configuration (configKey, configSection, dataType, maxLength, value) VALUES ('APPTITLE', 'ApplicationSettings', 'String', 50, '_APPTITLE_');
INSERT INTO configuration (configKey, configSection, dataType, maxLength, value) VALUES ('APPSLOGAN', 'ApplicationSettings', 'String', 50, '_APPSLOGAN_');
INSERT INTO configuration (configKey, configSection, dataType, maxLength, value) VALUES ('CURRENCY', 'ApplicationSettings', 'String', 10, '_CURRENCY_');
INSERT INTO configuration (configKey, configSection, dataType, maxLength, value) VALUES ('TAXLABEL', 'ApplicationSettings', 'String', 10, '_TAXLABEL_');
INSERT INTO configuration (configKey, configSection, dataType, maxLength, value) VALUES ('BUSINESSLOGOURL', 'Business', 'String', '50', '_BUSINESSLOGOURL_');
INSERT INTO configuration (configKey, configSection, dataType, maxLength, value) VALUES ('EMAILINVOICETEMPLATE', 'Email', 'String', 200, 'Dear [ClientName],\nPlease find the invoice [InvoiceNo] for amount [InvoiceAmount] attached.\nRegards,\n[BusinessName]');
INSERT INTO configuration (configKey, configSection, dataType, maxLength, value) VALUES ('INVOICETEMPLATENAME', 'InvoiceTemplates', 'String', 10, '_INVOICETEMPLATE_');