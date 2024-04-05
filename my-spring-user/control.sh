#!/bin/bash
# Description: control.sh start stop restart status
# Usage: control.sh {start|stop|restart|status}
# Author: Emlyn Ma

# exit when any command fails
set -e

work_dir=$(cd "$(dirname "${0}")" || exit 1; pwd)
cd "${work_dir}" || exit 1

app=my-spring-user

app_jar=${app}.jar
app_pid=var/${app}.pid
app_env=$(if [ -n "${2}" ]; then echo "${2}"; else echo "dev"; fi)

java_home=$(which java | head -1)
# java_args="-Xms512M -Xmx1024M -Xmn192M -Xss512K"

function get_pid() {
  pid=""
  # get pid from pid file
  if [ -f ${app_pid} ]; then
    pid=$(cat ${app_pid})
  fi
  # get pid from pgrep
  if [ -z "${pid}" ]; then
    pid=$(pgrep -f "${app_jar}" | head -1)
  fi
  echo "${pid}"
}

function is_running() {
    pid=$(get_pid)
    # check pid is empty
    if [ -z "${pid}" ]; then
      return 1
    fi
    # check process is running
    if ps -p "${pid}" >/dev/null 2>&1; then
      return 0
    else
      return 1
    fi
}

function start() {
  # create log and var dir
  mkdir -p log >/dev/null 2>&1
  mkdir -p var >/dev/null 2>&1
  # check app is running
  if is_running; then
    echo "${app} is already running."
    exit 0
  fi
  # start app
  nohup "${java_home}" -jar ${app_jar} --spring.profiles.active="${app_env}" >/dev/null 2>&1 &
  # save pid to pid file
  echo $! > ${app_pid}
  # check app start is success
  sleep 1
  if is_running; then
    echo "${app} is running."
    return 0
  else
    # remove pid file
    rm -f ${app_pid}
    echo "${app} start failed, please check."
    exit 1
  fi
}

function stop() {
  # check app is running
  if ! is_running; then
    echo "${app} is already stopped."
    exit 0
  fi
  # stop app until app is stopped
  for ((i = 0; i < 30; i++)); do
    if is_running; then
      kill -15 "$(get_pid)" >/dev/null 2>&1
      sleep 1
    else
      # remove pid file
      rm -f ${app_pid}
      echo "${app} is stopped."
      return 0
    fi
  done
  echo "${app} stop failed, please check."
  exit 1
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