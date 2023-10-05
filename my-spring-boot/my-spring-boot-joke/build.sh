#!/bin/bash
# description: build script for this project (maven)
# usage: sh ./build.sh
# author: Emlyn Ma

# exit when any command fails
set -e

mvn clean package -e -U -DskipTests

rm -rf output && mkdir output

cp control.sh output/
mv target/*.jar output/

rm -rf target
