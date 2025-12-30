#!/bin/bash
# Description: control.sh start stop restart status
# Usage: control.sh {start|stop|restart|status}
# Author: Emlyn Ma

set -euo pipefail

cd "$(dirname "${0}")"

app=trade

app_jar="${app}.jar"
app_pid="./var/${app}.pid"
app_env="${2:-dev}"
app_cmd="$(command -v java)"
app_arg=""
# app_arg="-Xms512M -Xmx1024M -Xmn192M -Xss512K"

function get_pid() {
  local pid=""
  # get pid from pid file
  if [[ -f "${app_pid}" ]]; then
    pid=$(cat "${app_pid}")
  fi
  # get pid from pgrep
  if [[ -z "${pid}" ]]; then
    pid=$(pgrep -f "${app_jar}" | head -n 1)
  fi
  echo "${pid}"
}

function is_running() {
  # check process is running
  [[ -n "$(get_pid)" ]] && kill -0 "$(get_pid)" >/dev/null 2>&1
}

function start() {
  # check app is running ?
  if is_running; then
    echo "${app} is already running"
    return 0
  fi
  # create log and var dir
  mkdir -p log >/dev/null 2>&1
  mkdir -p var >/dev/null 2>&1
  # start app
  echo "starting ${app} with profile: ${app_env} ..."
  nohup "${app_cmd}" -jar "${app_jar}" "${app_arg}" --spring.profiles.active="${app_env}" >/dev/null 2>&1 &
  echo $! > "${app_pid}"
  # wait for start result
  sleep 1
  # check start result
  if is_running; then
    echo "${app} started successfully"
    return 0
  else
    rm -f "${app_pid}"
    echo "${app} failed to start, please check"
    return 1
  fi
}

function stop() {
  # check app is running ?
  if ! is_running; then
    echo "${app} is already stopped"
    return 0
  fi
  # stop app until app is stopped
  echo "stopping ${app} ..."
  for ((i = 0; i < 30; i++)); do
    kill -15 "$(get_pid)" >/dev/null 2>&1
    sleep 1
    if ! is_running; then
      rm -f "${app_pid}"
      echo "${app} stopped successfully"
      return 0
    fi
  done
  echo "graceful shutdown failed, forcing stop..."
  kill -9 "$(get_pid)"
  rm -f "${app_pid}"
  echo "${app} has been forcefully stopped"
}

function restart() {
  if is_running; then
    stop
  fi
  start
}

function status() {
  if is_running; then
    echo "${app} is running"
    echo "pid is $(get_pid)"
    echo "env is ${app_env}"
  else
    echo "${app} is stopped"
  fi
}

case "${1}" in
  start)
    start
    ;;
  stop)
    stop
    ;;
  restart)
    restart
    ;;
  status)
    status
    ;;
  *)
    echo "Usage: ${0} {start|stop|restart|status}"
    exit 1
esac