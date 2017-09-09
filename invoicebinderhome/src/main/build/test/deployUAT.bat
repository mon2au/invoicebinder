@echo off
rem **** UAT DEPLOYMENT ****

set ibhome="C:\\Users\\Manpreet\\GitHub\\invoicebinder\\invoicebinder"
set ibhomehome="C:\\Users\\Manpreet\\GitHub\\invoicebinder\\invoicebinderhome"

echo "Build Invoicebinder"
call mvn -f %ibhome% clean install -Puat,context
echo "Build Invoicebinderhome"
call mvn -f %ibhomehome% clean install

echo "Finished Building Invoicebinder"
winscp /script=uat-winscp.script
