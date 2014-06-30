
import hashlib
import os
import tarfile
from urllib.request import urlopen


AILURUS_CACHE = '/opt/ailurus/tmp/cache/'


class ChecksumMismatch(Exception):
    pass


def _CheckSignature(f, checksum):
    m = hashlib.md5()
    m.update(f)
    return m.hexdigest() == checksum


def _GetMembers(tar, prefix):
    if not prefix.endswith('/'):
        prefix += '/'
    offset = len(prefix)
    for tarinfo in tar.getmembers():
        if tarinfo.name.startswith(prefix):
            tarinfo.name = tarinfo.name[offset:]
            yield tarinfo


# Extracts a tarball to the extract_path
def ExtractTarball(path_to_package, extract_path, subdir=None):
    tar_file = tarfile.open(path_to_package)
    members = None
    if subdir is not None:
        members = _GetMembers(tar_file, subdir)
    tar_file.extractall(extract_path, members)


def GetPackage(src, checksum):
    print('Grabbing %s.' % src)
    filepath = AILURUS_CACHE + checksum

    if os.path.isfile(filepath):
        with open(filepath, 'rb') as f:
            if _CheckSignature(f.read(), checksum):
                print('File was found in cache.')
                return filepath

    print('File not found. Downloading file.')
    package = urlopen(src).read()
    if _CheckSignature(package, checksum):
        with open(filepath, 'wb') as f:
            f.write(package)
        return filepath

    raise ChecksumMismatch(
        'Checksum mismatch. Please check your download link.')
