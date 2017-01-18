@echo off
setlocal enabledelayedexpansion

for /f tokens^=2-5^ delims^=.-^" %%j in ('java -fullversion 2^>^&1') do (
	set jver=%%k
)
if (%jver% LSS 8) goto USAGE_JAVA
goto RUN_1

:USAGE_JAVA
echo Running this application requires Java 8. 
set JAVA_HOME=H:\Java\jdk1.8.0_25
goto RUN_2


:RUN_1

@call java -Xmx1024m -Xms256m -Dlog4j.configuration=config/log4j.properties -cp "lib/*;ef-extractor-2016.06.0-SNAPSHOT.jar" gov.va.vinci.ef.Client -clientConfigFile "config/ClientConfig.groovy" -readerConfigFile "config/readers/KnowtatorCollectionReaderConfig.groovy" -listenerConfigFile "config/listeners/SimpleXmiListenerConfig.groovy"

:RUN_2
@call %JAVA_HOME%/bin/java -Xmx1024m -Xms256m -Dlog4j.configuration=config/log4j.properties -cp "lib/*;ef-extractor-2016.06.0-SNAPSHOT.jar" gov.va.vinci.ef.Client -clientConfigFile "config/ClientConfig.groovy" -readerConfigFile "config/readers/KnowtatorCollectionReaderConfig.groovy" -listenerConfigFile "config/listeners/SimpleXmiListenerConfig.groovy"
:: @call %JAVA_HOME%/bin/java -Xmx1024m -Xms256m -Dlog4j.configuration=config/log4j.properties -cp "lib/*;ef-extractor-2016.06.0-SNAPSHOT.jar" gov.va.vinci.ef.Client -clientConfigFile "config/ClientConfig.groovy" -readerConfigFile "config/readers/FileCollectionReaderConfig.groovy" -listenerConfigFile "config/listeners/SimpleXmiListenerConfig.groovy"
:: @call %JAVA_HOME%/bin/java -Xmx1024m -Xms256m  -Djava.library.path=config -Dlog4j.configuration=config/log4j.properties -cp "lib/*;ef-extractor-2016.06.0-SNAPSHOT.jar" gov.va.vinci.ef.Client -clientConfigFile "config/ClientConfig.groovy" -readerConfigFile "config/readers/BatchDatabaseCollectionReaderConfig.groovy" -listenerConfigFile "config/listeners/DatabaseListenerConfig.groovy"
pause
