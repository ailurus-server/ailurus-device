import package
import unittest
import mock


class TestPackager(unittest.TestCase):
    def setUp(self):
        self.prepare_patcher = mock.patch('package._PreparePackage')
        self.os_patcher = mock.patch('package.os.chdir')

        self.mock_prepare = self.prepare_patcher.start()
        self.mock_os = self.os_patcher.start()
        self.mock_module = mock.Mock()

        self.mock_prepare.return_value = ('/tmp', self.mock_module)
        self.test_env = {'Directory': '/opt/ailurus/www/wordpress'}

    def tearDown(self):
        self.prepare_patcher.stop()
        self.os_patcher.stop()

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
