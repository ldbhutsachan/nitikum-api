@echo off

set /p VERSION= Enter image version:

if defined VERSION GOTO :version_arg_exists

set VERSION=latest

:version_arg_exists

echo %VERSION%

set IMAGE=khounkham/logistics-api

echo Building image: %IMAGE%:%VERSION%

cmd /c mvn clean dependency:tree compile package

cmd /c docker build -t %IMAGE%:%VERSION% .
cmd /c docker push %IMAGE%:%VERSION%


