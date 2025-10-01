#!/bin/bash

# 严格模式
set -euo pipefail

# 切换到脚本所在目录
cd "$(dirname "${0}")"

# 清理项目
sh clean.sh

# 安装 ms-core 模块
mvn install -pl ms-core -am -DskipTests

# 安装ms-data 模块
mvn install -pl ms-data -am -DskipTests