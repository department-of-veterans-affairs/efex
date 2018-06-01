#!/bin/bash

java -Dlog4j.configurationFile=config/log4j.properties -cp "config/*:lib/*:ef-extractor-2018.05.0-SNAPSHOT.jar" gov.va.vinci.ef.Service

#java -Xmx1024m -Xms256m -Dlog4j.configurationFile=config/log4j.properties -cp "config/*:target/lib/*:target/ef-extractor-2018.05.0-SNAPSHOT.jar" gov.va.vinci.ef.Service


