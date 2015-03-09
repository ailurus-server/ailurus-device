'use strict'

var dashboardControllers = angular.module('dashboardControllers', []);

dashboardControllers.controller('AppCtrl', [
    '$scope', '$location', 'Session',
    function ($scope, $location, Session) {
        Session.loadSession();

        $scope.user = {
            name: Session.username,
            email: Session.email,
        }

        $scope.app = {
            panel: 'device',
            showNavBar: false,
        };

        $scope.errorMessages = [];
        $scope.addErrorMessage = function(message) {
            $scope.errorMessages.push(message);
        };

        $scope.logOut = function () {
            Session.clearSession();
            $location.path("/login");
        }

        $scope.removeErrorMessage = function() {
            $scope.errorMessages.pop();
        };
    }
]);

dashboardControllers.controller('DeviceCtrl', [
    '$scope', 'Api',
    function ($scope, Api) {
        $scope.app.panel = 'device';
        $scope.app.showNavBar = true;

        $scope.device = Api.queryOne('device');
    }
]);

dashboardControllers.controller('UsersCtrl', [
    '$scope', 'Api',
    function ($scope, Api) {
        $scope.app.panel = 'users';
        $scope.app.showNavBar = true;

        $scope.users = Api.queryMany('users');

        $scope.newName = '';
        $scope.changeName = function() {
            var updatedUser = {
                name: $scope.newName,
            };

            Api.post('users/' + $scope.user.name, updatedUser)
               .success(function() {
                    $scope.user.name = $scope.newName;
                    $scope.newName = '';
                    $scope.reloadUsers();
               })
               .error(function() {
                    $scope.addErrorMessage('Failed to change name due to server error.');
               })
        }

        $scope.newEmail = '';
        $scope.changeEmail = function() {
            var updatedUser = {
                email: $scope.newEmail,
            };

            Api.post('users/' + $scope.user.name, updatedUser)
               .success(function() {
                    $scope.user.email = $scope.newEmail;
                    $scope.newEmail = '';
               })
               .error(function() {
                    $scope.addErrorMessage('Failed to change email due to server error.');
               })
        }

        $scope.newPassword = '';
        $scope.newConfirmation = '';
        $scope.changePassword = function() {
            var updatedUser = {
                password: $scope.newPassword,
            };

            Api.post('users/' + $scope.user.name, updatedUser)
               .success(function() {
                    $scope.user.password = $scope.newPassword;
                    $scope.newPassword = '';
                    $scope.newConfirmation = '';
               })
               .error(function() {
                    $scope.addErrorMessage('Failed to change password due to server error.');
               })
        }

        $scope.userToAdd = {
            name: '',
            email: '',
            password: '',
            confirmation: '',
        };
        $scope.addUser = function() {
            var newUser = {
                name: $scope.userToAdd.name,
                email: $scope.userToAdd.email,
                password: $scope.userToAdd.password,
            };

            Api.put('users', newUser)
               .success(function() {
                    $scope.userToAdd = {
                        name: '',
                        email: '',
                        password: '',
                        confirmation: '',
                    };

                    $scope.reloadUsers();
               })
               .error(function(data) {
                    $scope.addErrorMessage('Failed to add the user due to server error.');
               });
        }

        $scope.userToDelete = null;
        $scope.setUserToDelete = function(user) {
            $scope.userToDelete = user;
        }
        $scope.deleteUser = function() {
            Api.delete('users/' + $scope.userToDelete.name)
               .success(function() {
                    $scope.userToDelete = null;
                    $scope.reloadUsers();
               })
               .error(function(data) {
                    $scope.addErrorMessage('Failed to delete the user due to server error.');
               });
        }

        $scope.reloadUsers = function() {
            $scope.users = Api.queryMany('users');
        }
    }
]);

dashboardControllers.controller('StoreCtrl', [
    '$scope', '$location', 'Api', 'Apps',
    function ($scope, $location, Api, Apps) {
        $scope.app.panel = 'store';
        $scope.app.showNavBar = true;

        $scope.searchOnEnter = function($event) {
            if ($event.keyCode == 13) {
                $scope.search();
            }
        }

        $scope.search = function() {
            var keyword = $scope.keyword || "";
            if (keyword.trim() == "") {
                $location.url("/store")
            } else {
                $location.url("/store/search/" + encodeURIComponent(keyword));
            }
        }

        $scope.featuredApps = [];
        $scope.reloadApps = function() {
            if ($location.url() != "/store") {
                $scope.reloading = false;
                return;
            }

            $scope.reloading = true;
            Api.queryMany('apps/featured')
            .$promise.then(function(apps) {
                if (Apps.hasInProgress(apps)) {
                    setTimeout($scope.reloadApps, 1000);
                } else {
                    $scope.reloading = false;
                }

                $scope.featuredApps = apps;
            },
            function(reason) {
                setTimeout($scope.reloadApps, 60000);
            });
        };

        $scope.reloadApps();

        $scope.install = function(app) {
            Api.put('apps/named/' + encodeURIComponent(app.name))
               .success(function(data) {
                $scope.reloadApps();
               })
               .error(function(data) {
                $scope.addErrorMessage('Failed to install app due to server error.');
               });
        };
    }
]);

dashboardControllers.controller('SearchCtrl', [
    '$scope', '$location', '$routeParams', 'Api', 'Apps',
    function ($scope, $location, $routeParams, Api, Apps) {
        $scope.app.panel = 'store';
        $scope.app.showNavBar = true;

        $scope.getTitleLead = function() {
            return 'Results for ';
        };

        $scope.getTitleKeyword = function() {
            return $routeParams.keyword;
        };

        $scope.searchOnEnter = function($event) {
            if ($event.keyCode == 13) {
                $scope.search();
            }
        };

        $scope.search = function() {
            var keyword = $scope.keyword || "";
            if (keyword.trim() != "") {
                $location.url("/store/search/" + encodeURIComponent(keyword));
            }
        };

        $scope.results = [];
        $scope.reloadApps = function() {
            if ($location.url().substring(0, 14) != "/store/search/") {
                $scope.reloading = false;
                return;
            }

            $scope.reloading = true;
            Api.queryMany('apps/search/:k', {k: $routeParams.keyword})
            .$promise.then(function(apps) {
                if (Apps.hasInProgress(apps)) {
                    setTimeout($scope.reloadApps, 1000);
                } else {
                    $scope.reloading = false;
                }

                $scope.results = apps;
            },
            function(reason) {
                setTimeout($scope.reloadApps, 60000);
            });
        };

        $scope.reloadApps();

        $scope.install = function(app) {
            Api.put('apps/named/' + encodeURIComponent(app.name))
               .success(function(data) {
                if (!$scope.reloading) {
                    $scope.reloadApps();
                }
               })
               .error(function(data) {
                $scope.addErrorMessage('Failed to install app due to server error.');
               });
        };
    }
]);

dashboardControllers.controller('AppsCtrl', [
    '$scope', '$location', 'Api', 'Apps',
    function ($scope, $location, Api, Apps) {
        $scope.app.panel = 'app';
        $scope.app.showNavBar = true;

        $scope.apps = [];

        $scope.appToUninstall = null;
        $scope.confirmUninstall = function(app) {
            $scope.appToUninstall = app;
        };

        $scope.uninstall = function() {
            var app = $scope.appToUninstall;
            Api.delete('apps/named/' + encodeURIComponent(app.name))
            .success(function(data) {
                $scope.appToUninstall = null;
                if (!$scope.reloading) {
                    $scope.reloadApps();
                } else {
                    $scope.reloading = false;
                }
            })
            .error(function(data) {
                $scope.addErrorMessage('Failed to uninstall app due to server error.');
            });
        };

        $scope.apps = [];
        $scope.reloadApps = function() {
            if ($location.url() != "/apps") {
                $scope.reloading = false;
                return;
            }

            $scope.reloading = true;
            Api.queryMany('apps/installed')
            .$promise.then(function(apps) {
                if (Apps.hasInProgress(apps)) {
                    setTimeout($scope.reloadApps, 1000);
                }

                $scope.apps = apps;
            },
            function(reason) {
                setTimeout($scope.reloadApps, 60000);
            });
        };

        $scope.reloadApps();
    }
]);

dashboardControllers.controller('SupportCtrl', ['$scope',
    function ($scope) {
        $scope.app.panel = 'support';
        $scope.app.showNavBar = true;
    }
]);

dashboardControllers.controller('LoginCtrl', [
    '$scope', '$http', '$location', 'Session', 'Api',
    function ($scope, $http, $location, Session, Api) {
        $scope.app.showNavBar = false;

        $scope.login = function() {
            if (!($scope.cred.username && $scope.cred.password)) {
                return;
            }

            $scope.removeErrorMessage();

            Api.post('users/' + $scope.cred.username + '/login', $scope.cred.password)
            .success(function(user) {
                Session.username = user.name;
                Session.token = user.name + ":" + user.password;
                Session.email = user.email;
                Session.saveSession();
                $location.path("/");
            })
            .error(function(data) {
                $scope.addErrorMessage('Wrong username or password');
            });
        }
    }
]);