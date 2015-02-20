#!/usr/bin/python3

from setuptools import setup, find_packages

setup(
    name='ailurus',
    version='0.0.2',
    author='ailurus',
    description='Ailurus Python utilities',
    entry_points={
        'console_scripts': [
            'ai-get = ailurus.package:Main',
            'ailurusd = ailurusd.main:Main'
            ],
        },
    install_requires=['tornado==4.1','netifaces==0.10.0', 'pymysql'],
    packages=find_packages(),
)
