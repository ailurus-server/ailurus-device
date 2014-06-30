#!/usr/bin/python3

from setuptools import setup

setup(
    name='ailurus',
    version='0.0.1',
    author='ailurus',
    description='Ailurus Python utilities',
    entry_points={
        'console_scripts': [
            'ai-get = ailurus.package:Main'
            ],
        },
    packages=['ailurus'],
)
