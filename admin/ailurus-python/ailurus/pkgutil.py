
import hashlib
import os
import urllib2


AILURUS_CACHE = 'opt/ailurus/tmp/cache'


class ChecksumMismatch(Exception):
    pass


def _CheckSignature(f, checksum):
    m = hashlib.md5()
    m.update(f)
    return m.hexdigest() == checksum


def GetPackage(src, checksum):
    print('Grabbing %s.' % src)
    filepath = AILURUS_CACHE + checksum

    if os.path.isfile(filepath):
        with open(filepath, 'r') as f:
            if _CheckSignature(f.read(), checksum):
                print('File was found in cache.')
                return filepath

    print('File not found. Downloading file.')
    package = urllib2.urlopen(src).read()
    if _CheckSignature(package, checksum):
        with open(filepath, 'wb') as f:
            f.write(package)
        return filepath

    raise ChecksumMismatch(
        'Checksum mismatch. Please check your download link.')
