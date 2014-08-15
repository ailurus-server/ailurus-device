'use strict'

var dashboardControllers = angular.module('dashboardControllers', []);

dashboardControllers.controller('AppCtrl', [
    '$scope', 'loginUser', 'Session',
    function ($scope, loginUser, Session) {
        $scope.user = {
            name: Session.username
        }
        $scope.app = {
            panel: 'device',
            showNavBar: false,
        };
    }
]);

dashboardControllers.controller('DeviceCtrl', [
    '$scope', '$location','$anchorScroll', 'Device',
    function ($scope, $location, $anchorScroll, Device) {
        $scope.app.panel = 'device';
        $scope.app.showNavBar = true;

        $scope.device = Device.query();

        // TODO use .affix and .scrollSpy on the right hand side nav bar
        $scope.scroll = function(event) {
            var hash = event.target.hash;
            $location.hash(hash.substring(1));
            $anchorScroll();
            event.preventDefault();
        };
    }
]);

dashboardControllers.controller('UsersCtrl', ['$scope',
    function ($scope) {
        $scope.app.panel = 'users';
        $scope.app.showNavBar = true;

        $scope.scroll = function(event) {
            var hash = event.target.hash;
            $location.hash(hash.substring(1));
            $anchorScroll();
            event.preventDefault();
        };
    }
]);

dashboardControllers.controller('StoreCtrl', ['$scope',
    function ($scope) {
        $scope.app.panel = 'store';
        $scope.app.showNavBar = true;

        $scope.scroll = function(event) {
            var hash = event.target.hash;
            $location.hash(hash.substring(1));
            $anchorScroll();
            event.preventDefault();
        };
    }
]);

dashboardControllers.controller('AppsCtrl', ['$scope', '$http', 'API_BASE',
    function ($scope, $http, API_BASE) {
        $scope.app.panel = 'app';
        $scope.app.showNavBar = true;

        $scope.scroll = function(event) {
            var hash = event.target.hash;
            $location.hash(hash.substring(1));
            $anchorScroll();
            event.preventDefault();
        };

        $scope.apps = [];

        $scope.init = function() {
            $http({url: API_BASE + 'apps/installed', method: 'GET'})
            .success(function(data) {
                $scope.apps = data;
            });
        }
        $scope.init();
    }
]);

dashboardControllers.controller('SupportCtrl', ['$scope',
    function ($scope) {
        $scope.app.panel = 'support';
        $scope.app.showNavBar = true;

        $scope.scroll = function(event) {
            var hash = event.target.hash;
            $location.hash(hash.substring(1));
            $anchorScroll();
            event.preventDefault();
        };
    }
]);

dashboardControllers.controller('LoginCtrl', ['API_BASE', '$scope', '$http', '$location', 'Session',
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
                url: API_BASE + 'accounts/' + $scope.cred.username + '/login',
                data: $scope.cred,
                method: 'POST'})
            .success(function(data) {
                Session.username = $scope.cred.username;
                Session.token = data.token;
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