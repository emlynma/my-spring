#!/bin/bash
# Description: Build script for the project
# Usage: sh build.sh
# Author: Emlyn Ma

set -e

mvn clean package -e -U -DskipTests

rm -rf output && mkdir output

cp control.sh output/
mv target/*.jar output/

rm -rf target