@echo off
rem **** TEST DEPLOYMENT ****

set ibhome="C:\\Users\\Manpreet\\GitHub\\invoicebinder\\invoicebinder"

echo "Build Invoicebinder"
start /wait mvn -f %ibhome% clean install -Ptest -Pcontext
echo "Finished Building Invoicebinder"
winscp /script=test-winscp.script
