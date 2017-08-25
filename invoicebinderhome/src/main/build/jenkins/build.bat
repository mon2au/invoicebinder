@echo off
rem InvoiceBinder build script
rem Calls jenkins build project for invoicebinder.
rem Uses basic authentication
rem accepts parameter for environment else builds the uat environment by default
cls
set environment=%1
set jenkinshost=localhost
set jenkinsport=8081

rem validate environment must be in "dev","uat", or "prod".
rem if environment is blank then it builds the "uat" environment.
rem default environment to uat if it is not passed.

if "%environment%" == "" (
    set environment=uat
)

rem validate environment codes
set result=false
if "%environment%" == "dev" set result=true
if "%environment%" == "uat" set result=true
if "%environment%" == "prod" set result=true

if NOT "%result%" == "true" (
    echo "%environment% is not a valid environment code. Must be either 'dev', 'uat' or 'prod'"
    exit /b
)

rem setting default branch
if "%environment%" == "prod" (
    set branch=integration
) ELSE (
    set branch=develop
)

echo "Building environment - %environment%"
echo "Building branch - %branch%"
echo "Starting build [InvoiceBinder] - %environment%"
echo "Using password: %PASSPHRASE%"
wget --spider --auth-no-challenge --http-user=mon2 --http-password="%PASSPHRASE%" "http://%jenkinshost%:%jenkinsport%/job/invoicebinder/buildWithParameters?token=286a9fc7-1438-4632-a08f-cbcd6ce68f8f&BUILD_TYPE=%environment%&BUILD_VERSION_MAJOR=1&BUILD_VERSION_MINOR=13&BUILD_BRANCH=%branch%"
echo "Trigger completed, check progress in Jenkins."

