@echo off
rem InvoiceBinder deploy script
rem Calls jenkins deploy project for invoicebinder.
rem Uses basic authentication
rem accepts parameter for environment else builds the uat environment by default
cls
set environment=%1
set jenkinshost=localhost
set jenkinsport=8081

rem validate environment must be in "uat", or "prod".
rem if environment is blank then it deploys to the "uat" environment.
rem default environment to uat if it is not passed.

if "%environment%" == "" (
    set environment=uat
)

rem validate environment codes
set result=false
if "%environment%" == "uat" set result=true
if "%environment%" == "prod" set result=true

if NOT "%result%" == "true" (
    echo "%environment% is not a valid environment code. Must be either 'uat' or 'prod'"
    exit /b
)

echo "Deploying to environment - %environment%"
echo "Starting deployment [InvoiceBinder] - %environment%"
echo "Using password: %PASSPHRASE%"
wget --spider --auth-no-challenge --http-user=mon2 --http-password="%PASSPHRASE%" "http://%jenkinshost%:%jenkinsport%/job/invoicebinder deployer/buildWithParameters?token=ed37a97a-18d3-4bec-98d8-383734157e44&BUILD_TYPE=%environment%&SSH_SERVER=mars&SSH_USER=mon2"
echo "Trigger completed, check progress in Jenkins."




