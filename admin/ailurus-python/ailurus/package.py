#!/usr/bin/python3

import imp
import os
import sys
import shutil
import tarfile
import tempfile

from ailurus import pkgutil

INSTALL_MOD_NAME = 'ai-package'


# Package context manager
# Extracts a package to a temporary directory, and changes the current working
# directory to that path. Upon entry, returns the main setup module. Upon exit,
# removes the temporary directory.
class PackageContext(object):
    def __init__(self, pkg):
        self.pkg = pkg
        self.temp_dir = tempfile.mkdtemp()

    def __enter__(self):
        pkgutil.ExtractTarball(self.pkg, self.temp_dir)
        self.old_path = os.getcwd()
        os.chdir(self.temp_dir)
        mod_path = os.path.join(self.temp_dir, INSTALL_MOD_NAME)
        return imp.load_source(INSTALL_MOD_NAME, mod_path)

    def __exit__(self, type, value, tb):
        shutil.rmtree(self.temp_dir)
        os.chdir(self.old_path)


# Installs a package given a path to the recipe, and the configuration
# environment
def InstallPackage(path_to_package, conf_env=None):
    with PackageContext(path_to_package) as pkg:
        pkg.install(conf_env)


# Removes a package given a path to the recipe, and the configuration
# environment
def RemovePackage(path_to_package, conf_env=None):
    with PackageContext(path_to_package) as pkg:
        pkg.remove(conf_env)


# Configures a package given a path to the recipe, and the configuration
# environment
def ConfigurePackage(path_to_package, conf_env=None):
    with PackageContext(path_to_package) as pkg:
        pkg.configure(conf_env)


def Main():
    method = sys.argv[1]
    path = sys.argv[2]
    if method == 'install':
        InstallPackage(path)
    elif method == 'remove':
        RemovePackage(path)
    else:
        print "Usage: ai-get [install|remove] [path-to-apkg]"


if __name__ == "__main__":
    Main()
