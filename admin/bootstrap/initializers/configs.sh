#!/bin/sh

echo "Creating 404"
echo "<html>The requested page could not be found.</html>" > /opt/ailurus/www/404.html

echo "Copying custom nginx configs"
cp confs/nginx.conf /etc/nginx/nginx.conf
cp confs/nginx-default /etc/nginx/sites-available/default

echo "Copying ailurus daemon configs"
cp nginx-confs/* /opt/ailurus/nginx-confs-servers/

echo "Copying ailurus shell config"
echo "ON_AILURUS=true" >> /etc/environment

echo "Copying supervisor configs"
cp supervisor-confs/* /etc/supervisor/conf.d/
