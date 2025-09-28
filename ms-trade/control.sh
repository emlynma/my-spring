#!/bin/bash
# Description: control.sh start stop restart status
# Usage: control.sh {start|stop|restart|status}
# Author: Emlyn Ma

set -euo pipefail

cd "$(dirname "${0}")"

app=ms-trade

app_jar=${app}.jar
app_pid=var/${app}.pid
app_env="${2:-dev}"
app_cmd=$(command -v java)
app_arg=""
# app_arg="-Xms512M -Xmx1024M -Xmn192M -Xss512K"

function get_pid() {
  local pid=""
  # get pid from pid file
  [[ -f ${app_pid} ]] && pid=$(cat "${app_pid}")
  # get pid from pgrep
  [[ -z "${pid}" ]] && pid=$(pgrep -f "${app_jar}" | head -n 1)
  echo "${pid}"
}

function is_running() {
  # check pid and pgrep
  [[ -n "$(get_pid)" ]] && ps -p "$(get_pid)" >/dev/null 2>&1
}

function start() {
  # create log and var dir
  mkdir -p log >/dev/null 2>&1
  mkdir -p var >/dev/null 2>&1
  # check app is running
  if is_running; then
    echo "${app} is already running."
    return 0
  fi
  # start app
  echo "starting ${app} with profile: ${app_env} ..."
  nohup "${app_cmd}" -jar "${app_jar}" "${app_arg}" --spring.profiles.active="${app_env}" >/dev/null 2>&1 &
  # save pid to pid file
  echo $! >${app_pid}
  # check app start is success
  sleep 1
  if is_running; then
    echo "${app} started successfully."
  else
    # remove pid file
    rm -f ${app_pid}
    echo "${app} failed to start. please check."
    return 1
  fi
}

function stop() {
  # check app is running
  if ! is_running; then
    echo "${app} is already stopped."
    return 0
  fi
  # stop app until app is stopped
  echo "stopping ${app} ..."
  for ((i = 0; i < 30; i++)); do
    kill -15 "$(get_pid)" >/dev/null 2>&1 && sleep 1
    if ! is_running; then
      rm -f "${app_pid}"
      echo "${app} stopped successfully."
      return 0
    fi
  done
  echo "graceful shutdown failed, forcing termination..."
  kill -9 "$(get_pid)"
  rm -f "${app_pid}"
  echo "${app} has been forcefully stopped."
}

function restart() {
  if is_running; then
    stop
  fi
  start
}

function status() {
  if is_running; then
    echo "${app} is running."
    echo "pid is $(get_pid)."
    echo "env is ${app_env}."
  else
    echo "${app} is stopped."
  fi
}

case ${1} in
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