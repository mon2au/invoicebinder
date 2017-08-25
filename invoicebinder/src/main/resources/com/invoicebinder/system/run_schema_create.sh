#!/bin/bash
mysql -h "localhost" -u "root" "-ppasswd" < "../database/schema/schema.sql"
mysql -h "localhost" -u "root" "-ppasswd" < "../database/schema/schema_signupdb.sql"
mysql -h "localhost" -u "root" "-ppasswd" sys_signupdb < "../database/database/sys_signupdb.sql"
