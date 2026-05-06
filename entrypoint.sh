#!/bin/sh
set -e
# Render (and most PaaS) inject PORT. Tomcat default is 8080 - rewrite server.xml at boot.
PORT="${PORT:-8080}"
sed -i "s|port=\"8080\"|port=\"$PORT\"|" /usr/local/tomcat/conf/server.xml
exec catalina.sh run
