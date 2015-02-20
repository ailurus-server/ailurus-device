#!/bin/sh

echo 'mysql-server mysql-server/root_password password teamtba' | debconf-set-selections
echo 'mysql-server mysql-server/root_password_again password teamtba' | debconf-set-selections

echo "Creating directories"
mkdir -p /opt/ailurus/apps
mkdir -p /opt/ailurus/www
mkdir -p /opt/ailurus/admin
mkdir -p /opt/ailurus/nginx-confs-www
mkdir -p /opt/ailurus/nginx-confs-servers
mkdir -p /opt/ailurus/tmp/cache

echo "Creating users"
adduser nginx nginx

echo "Installing dependencies"
apt-get -y install python3-setuptools mysql-server nginx php5-fpm php5-gd php5-mysql python3-pip screen daemon supervisor
pip-3.2 install pymysql tornado==4.1 netifaces==0.10.0
