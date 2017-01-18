@echo off

set MAVEN_HOME=H:\Applications\apache-maven-3.0.4

if not defined JAVA_HOME goto SET_JAVA
:SET_JAVA
set JAVA_HOME=H:\Java\jdk1.8.0_25
goto RUN

:RUN

REM Install echoextractor(ef)
cd /d %~dp0
call %MAVEN_HOME%\bin\mvn clean package
call %MAVEN_HOME%\bin\mvn site:site

REM Create dist directory
mkdir dist
xcopy /s/e/i  target\lib\* dist\lib\
xcopy /s/e/i  config\* dist\config\
xcopy /s/e/i  resources\* dist\resources\
xcopy /s/e/i  data\test\* dist\data\test
xcopy /s/e/i  data\ReferenceStandard\* dist\data\ReferenceStandard
copy /Y runClient.* dist\
copy /Y runService.* dist\
copy /Y target\*.jar dist\

pause