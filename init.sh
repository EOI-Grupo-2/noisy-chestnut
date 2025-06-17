#!/bin/sh
# init.sh

echo "Estableciendo permisos para /var/lib/uploads"
mkdir -p /var/lib/uploads
chmod -R 777 /var/lib/uploads

exec /cnb/lifecycle/launcher