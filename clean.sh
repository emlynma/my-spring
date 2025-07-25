#!/bin/bash

# 严格模式
set -euo pipefail

# 切换到脚本所在目录
cd "$(dirname "${0}")"

# 清理 maven 编译生成的 class 文件
mvn clean

# 清理其他文件
find . -type d -name "log" -exec rm -rf {} +
find . -type d -name "logs" -exec rm -rf {} +
find . -type d -name "output" -exec rm -rf {} +