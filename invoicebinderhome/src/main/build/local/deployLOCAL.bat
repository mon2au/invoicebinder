@echo off
@echo off
rem **** LOCAL DEPLOYMENT ****

SET tomcatpackage="C:\\Program Files\\Apache Software Foundation\\Tomcat 9.0\\webapps\\invoicebinder.war"
SET ibhome="C:\\Users\\Manpreet\\GitHub\\invoicebinder\\invoicebinder"
SET buildpackage="%ibhome%\\target\\invoicebinder.war"

echo "Cleanup."
del %tomcatpackage% | more
echo "Cleanup Completed."
echo "Building Invoicebinder."
call mvn -f %ibhome% clean install -Ptest,context
echo "Finished Building Invoicebinder."
echo f | xcopy /f /y %buildpackage% %tomcatpackage% | more
echo "Deploy Completed."










