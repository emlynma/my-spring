#!/bin/bash
# Build script for the project
# Author: Emlyn Ma

mvn clean package -e -U -DskipTests

rm -rf output && mkdir output

cp control.sh output/
mv target/*.jar output/

rm -rf target
