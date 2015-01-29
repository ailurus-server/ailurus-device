'use strict'

var dashboardControllers = angular.module('dashboardControllers', []);

dashboardControllers.controller('AppCtrl', [
    '$scope', 'Session',
    function ($scope, Session) {
        $scope.user = {
            name: Session.username //TODO also load email and user type
        }
        $scope.app = {
            panel: 'device',
            showNavBar: false,
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

        $scope.errorMsg = '';

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
                    $scope.errorMsg = 'Failed to change name due to server error.';
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
                    $scope.errorMsg = 'Failed to change email due to server error.';
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
                    $scope.errorMsg = 'Failed to change password due to server error.';
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
                    $scope.errorMsg = "Failed to add the user due to server error.";
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
                    $scope.errorMsg = "Failed to delete the user due to server error.";
               });
        }

        $scope.dismissErrorMsg = function() {
            $scope.errorMsg = '';
        }

        $scope.reloadUsers = function() {
            $scope.users = Api.queryMany('users');
        }
    }
]);

dashboardControllers.controller('StoreCtrl', [
    '$scope', 'Api',
    function ($scope, Api) {
        $scope.app.panel = 'store';
        $scope.app.showNavBar = true;

        $scope.categorizedUseCases = Api.queryMany('apps/usecases');
    }
]);

dashboardControllers.controller('UseCaseCtrl', [
    '$scope', '$routeParams', 'Api',
    function ($scope, $routeParams, Api) {
        var usecase = $routeParams.usecase;

        $scope.app.panel = 'store';
        $scope.app.showNavBar = true;

        $scope.showSearch = false;

        $scope.getTitleLead = function() {
            return 'Apps for';
        };

        $scope.getTitleKeyword = function() {
            return $scope.data.displayName;
        };

        $scope.getResults = function() {
            return $scope.data.apps;
        };

        $scope.data = Api.queryOne('apps/usecase/:u', {u: usecase});
    }
]);

dashboardControllers.controller('SearchCtrl', [
    '$scope', '$routeParams', 'Api',
    function ($scope, $routeParams, Api) {
        var keyword = $routeParams.keyword;

        $scope.app.panel = 'store';
        $scope.app.showNavBar = true;

        $scope.showSearch = true;
        $scope.getTitleLead = function() {
            return 'Results for ';
        };

        $scope.getTitleKeyword = function() {
            return keyword;
        };

        $scope.getResults = function() {
            return $scope.apps;
        };

        $scope.apps = Api.queryMany('apps/search/:k', {k: keyword});
    }
]);

dashboardControllers.controller('AppsCtrl', ['$scope', 'Api',
    function ($scope, Api) {
        $scope.app.panel = 'app';
        $scope.app.showNavBar = true;

        $scope.apps = Api.queryMany('apps/installed');
    }
]);

dashboardControllers.controller('SupportCtrl', ['$scope',
    function ($scope) {
        $scope.app.panel = 'support';
        $scope.app.showNavBar = true;
    }
]);

dashboardControllers.controller('LoginCtrl', [
    'API_BASE', '$scope', '$http', '$location', 'Session',
    function (API_BASE, $scope, $http, $location, Session) {
        $scope.app.showNavBar = false;

        $scope.alert = {
            type: 'danger',
            msg: ''
        };

        $scope.login = function() {
            if (!($scope.cred.username && $scope.cred.password)) {
                return;
            }
            $http({
                url: API_BASE + 'users/' + $scope.cred.username + '/login',
                data: $scope.cred.password,
                method: 'POST'})
            .success(function(user) {
                $scope.user.name = user.name;
                $scope.user.email = user.email;
                Session.username = user.name;
                Session.token = user.name + ":" + user.password;
                Session.saveSession();
                $location.path("/");
            })
            .error(function(data) {
                $scope.alert.msg = 'Wrong username or password';
            });
        }

        $scope.dismiss = function() {
            $scope.alert.msg = '';
        }
    }
]);