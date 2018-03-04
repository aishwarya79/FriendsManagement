#!/bin/bash
nohup java -Dspring.config.location=src/main/resources/application.properties  -jar target/friendsmanagement-0.0.1-SNAPSHOT.jar &