#!/usr/bin/env sh
!bin/sh

mvn install:install-file -Dfile=gwt-visualization.jar -DgroupId=com.google.gwt.google-apis -DartifactId=gwt-visualization -Dversion=1.1.2 -Dpackaging=jar
