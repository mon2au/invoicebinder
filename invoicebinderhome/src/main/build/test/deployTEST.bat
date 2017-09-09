@echo off
rem **** TEST DEPLOYMENT ****

set ibhome="C:\\Users\\Manpreet\\GitHub\\invoicebinder\\invoicebinder"

echo "Build Invoicebinder"
call mvn -f %ibhome% clean install -Ptest,context

echo "Finished Building Invoicebinder"
winscp /script=test-winscp.script
