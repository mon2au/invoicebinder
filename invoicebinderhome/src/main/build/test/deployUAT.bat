@echo off
rem **** UAT DEPLOYMENT ****

set ibhome="C:\\Users\\Manpreet\\GitHub\\invoicebinder\\invoicebinder"

echo "Build Invoicebinder"
start /wait mvn -f %ibhome% clean install -Puat -Pcontext
echo "Finished Building Invoicebinder"
winscp /script=uat-winscp.script
