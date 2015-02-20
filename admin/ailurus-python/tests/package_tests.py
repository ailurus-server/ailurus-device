import unittest
import mock
from contextlib import contextmanager
from ailurus import package


def ContextFor(mock):
    @contextmanager
    def Passthrough(pkg):
        yield mock
    return Passthrough


class TestPackager(unittest.TestCase):

    def setUp(self):
        self.mock_module = mock.Mock()
        self.patcher = mock.patch('ailurus.package.PackageContext',
                                  ContextFor(self.mock_module))
        self.patcher.start()
        self.test_env = {'Directory': '/opt/ailurus/www/wordpress'}

    def tearDown(self):
        self.patcher.stop()

    def testInstallation(self):
        package.InstallPackage('/some/path', self.test_env)
        self.mock_module.install.assert_called_once_with(self.test_env)

    def testRemove(self):
        package.RemovePackage('/some/path', self.test_env)
        self.mock_module.remove.assert_called_once_with(self.test_env)

    def testConfigure(self):
        package.ConfigurePackage('/some/path', self.test_env)
        self.mock_module.configure.assert_called_once_with(self.test_env)

if __name__ == "__main__":
    unittest.main()
