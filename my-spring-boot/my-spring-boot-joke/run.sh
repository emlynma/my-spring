#!/bin/bash
# description: build script for this project (maven)
# usage: sh ./run.sh
# author: Emlyn Ma

# exit when any command fails
set -e

sh ./build.sh

env="dev"
if [ -n "${1}" ]; then
    env="${1}"
fi
sh ./output/control.sh restart "${env}"