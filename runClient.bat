@echo off
setlocal enabledelayedexpansion

for /f tokens^=2-5^ delims^=.-^" %%j in ('java -fullversion 2^>^&1') do (
	set jver=%%k
)
if (%jver% LSS 8) goto USAGE_JAVA
goto RUN_1

:USAGE_JAVA
echo Running this application requires Java 8. 
set JAVA_HOME=C:\Java\jdk1.8.0_144
goto RUN_2


:RUN_1
@call java -Dlog4j.configurationFile=config/log4j.properties -cp "config/*;lib/*;target/ef-extractor-2018.05.0-SNAPSHOT-jar-with-dependencies.jar" gov.va.vinci.ef.Client -clientConfigFile "config/ClientConfig.groovy" -readerConfigFile "config/readers/KnowtatorCollectionReaderConfig.groovy" -listenerConfigFile "config/listeners/SimpleXmiListenerConfig.groovy"

:RUN_2
@call %JAVA_HOME%/bin/java -Dlog4j.configurationFile=config/log4j.properties -cp "config/*;lib/*;ef-extractor-2018.05.0-SNAPSHOT.jar" gov.va.vinci.ef.Client -clientConfigFile "config/ClientConfig.groovy" -readerConfigFile "config/readers/KnowtatorCollectionReaderConfig.groovy" -listenerConfigFile "config/listeners/SimpleXmiListenerConfig.groovy"
pause
