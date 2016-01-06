#!/bin/bash
mvn clean install && mvn exec:java -Dexec.mainClass="com.rhc.drools.example.Runner"
