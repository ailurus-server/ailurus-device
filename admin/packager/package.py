import imp
import os
import tarfile
import tempfile

INSTALL_MOD_NAME = 'ai-package'


# Directory context manager
# Switches to a directory on entering context manager.  Restores original
# directory on exit
class DirContext:
    cwd = None
    original_dir = None

    def __init__(self, dirname):
        self.cwd = os.path.realpath(dirname)

    def __enter__(self):
        self.original_dir = os.getcwd()
        os.chdir(self.cwd)
        return self

    def __exit__(self, type, value, tb):
        os.chdir(self.original_dir)


# Extracts the package and returns the location where it is extracted.
# Uses a temporary directory if a path is not provided
def _ExtractPackage(path_to_package, extract_path=None):
    if extract_path is None:
        extract_path = tempfile.mkdtemp()
    package_archive = tarfile.open(path_to_package)
    package_archive.extractall(extract_path)
    return extract_path


# Loads the module that contains the instructions for package installation,
# configuration, and removal
def _PreparePackage(path_to_package):
    tmp_dir = _ExtractPackage(path_to_package)
    mod_path = os.path.join(tmp_dir, INSTALL_MOD_NAME)
    return tmp_dir, imp.load_source(INSTALL_MOD_NAME, mod_path)


# Installs a package given a path to the recipe, and the configuration
# environment
def InstallPackage(path_to_package, conf_env):
    tmp_dir, pkg = _PreparePackage(path_to_package)
    with DirContext(tmp_dir):
        pkg.install(conf_env)


# Removes a package given a path to the recipe, and the configuration
# environment
def RemovePackage(path_to_package, conf_env):
    tmp_dir, pkg = _PreparePackage(path_to_package)
    with DirContext(tmp_dir):
        pkg.remove(conf_env)


# Configures a package given a path to the recipe, and the configuration
# environment
def ConfigurePackage(path_to_package, conf_env):
    tmp_dir, pkg = _PreparePackage(path_to_package)
    with DirContext(tmp_dir):
        pkg.configure(conf_env)


if __name__ == "__main__":
    env = {}
    env['Destination'] = "/opt/ailurus/www/wordpress"
    pkg = "tests/wordpress/wordpress.apkg"
