#!/bin/bash
# Description: Build script for the project
# Usage: sh build.sh
# Author: Emlyn Ma

# exit when any command fails
set -e

../gradlew clean bootJar

rm -rf output && mkdir output

cp control.sh output/
mv build/libs/*.jar output/

../gradlew clean