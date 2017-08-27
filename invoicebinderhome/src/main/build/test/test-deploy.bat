@echo off
rem This script deploys invoicebinder to the test server.
rem Invoicebinder test server is a linux machine.
rem This script uses WinScp to copy the files.
rem WinScp must be installed.

winscp /script=test-winscp.script

