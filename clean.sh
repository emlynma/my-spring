#!/bin/bash
# Description: Clean up unnecessary files
# Usage: sh build.sh
# Author: Emlyn Ma

set -euo pipefail

# 切换到脚本所在目录
cd "$(dirname "${0}")"

# 清理 Maven 编译生成的 class 文件
mvn clean

# 查找并删除所有名为 logs 和 output 的目录
find . -type d -name "logs" -exec rm -rf {} +
find . -type d -name "output" -exec rm -rf {} +
