#!/bin/bash

# 严格模式
set -euo pipefail

# 切换到脚本所在目录
cd "$(dirname "${0}")"

# 安装父 POM 到本地仓库
mvn -N install

# 重新安装 common data 模块
mvn clean install -pl common,data -DskipTests

# 清理构建产物
mvn clean

# 清理运行产物
find . -type d -name "log" -exec rm -rf {} +
find . -type d -name "output" -exec rm -rf {} +