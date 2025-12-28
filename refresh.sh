#!/bin/bash

# 严格模式
set -euo pipefail

# 切换到脚本所在目录
cd "$(dirname "${0}")"

# 重新安装 common 模块
mvn clean install -pl common -am -DskipTests

# 重新安装 data 模块
mvn clean install -pl data -am -DskipTests

# 清理项目
sh clean.sh