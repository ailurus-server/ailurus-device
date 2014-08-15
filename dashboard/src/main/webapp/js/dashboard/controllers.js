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
    '$scope', '$location','$anchorScroll', 'Api',
    function ($scope, $location, $anchorScroll, Api) {
        $scope.app.panel = 'device';
        $scope.app.showNavBar = true;

        $scope.device = Api.queryOne('device');

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

dashboardControllers.controller('StoreCtrl', [
    '$scope', 'Api',
    function ($scope, Api) {
        $scope.app.panel = 'store';
        $scope.app.showNavBar = true;

        $scope.categorizedUseCases = Api.queryOne('apps/usecases')

        $scope.scroll = function(event) {
            var hash = event.target.hash;
            $location.hash(hash.substring(1));
            $anchorScroll();
            event.preventDefault();
        };
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

        $scope.scroll = function(event) {
            var hash = event.target.hash;
            $location.hash(hash.substring(1));
            $anchorScroll();
            event.preventDefault();
        };
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

        $scope.scroll = function(event) {
            var hash = event.target.hash;
            $location.hash(hash.substring(1));
            $anchorScroll();
            event.preventDefault();
        };
    }
]);

dashboardControllers.controller('AppsCtrl', ['$scope', 'Api',
    function ($scope, Api) {
        $scope.app.panel = 'app';
        $scope.app.showNavBar = true;

        $scope.scroll = function(event) {
            var hash = event.target.hash;
            $location.hash(hash.substring(1));
            $anchorScroll();
            event.preventDefault();
        };

        $scope.apps = Api.queryMany('apps/installed');
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