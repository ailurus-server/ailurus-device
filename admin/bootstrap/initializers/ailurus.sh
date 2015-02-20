#!/bin/sh

echo "Installing ailurus python utilities"
(cd ../ailurus-python && python3 setup.py install)

echo "Building packages"
(cd ../packs && ./build-packs)

