#!/bin/bash
mysql -h "localhost" -u "root" "-ppasswd" "sys_demodb" < "../database/storedproc/sp_get_rptIncomeAndExpense.sql"
mysql -h "localhost" -u "root" "-ppasswd" "sys_demodb" < "../database/storedproc/sp_get_rptSalesAndPayments.sql"