#!/bin/bash
echo "passwd" | sudo -S apt-get install -y maven2
mvn install:install-file -Dfile=../../../../lib/gwt-visualization.jar -DgroupId=com.google.gwt.google-apis -DartifactId=gwt-visualization -Dversion=1.1.2 -Dpackaging=jar
