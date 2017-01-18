#!/bin/bash

java -Xmx1024m -Xms256m -Dlog4j.configuration=config/log4j.properties -cp "config/*:lib/*:ef-extractor-2016.06.0-SNAPSHOT.jar" gov.va.vinci.ef.Service

java -Xmx1024m -Xms256m -Dlog4j.configuration=config/log4j.properties -cp "config/*:target/lib/*:target/ef-extractor-2016.06.0-SNAPSHOT.jar" gov.va.vinci.ef.Service


