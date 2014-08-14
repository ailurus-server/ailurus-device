'use strict'

var welcomeApp = angular.module('welcomeApp', [
    'ngRoute',
    'welcomeControllers'
]);

welcomeApp.constant('AILURUS_DOMAIN', 'ailurus.ca');
welcomeApp.constant('DEVICE_INIT_URL', '/api/init');

welcomeApp.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
            when('/', {
                templateUrl: '/welcome/welcome.html',
                controller: 'WelcomeCtrl'
            }).
            when('/user', {
                templateUrl: '/welcome/user.html',
                controller: 'UserCtrl'
            }).
            when('/name', {
                templateUrl: '/welcome/name.html',
                controller: 'NameCtrl'
            }).
            when('/online', {
                templateUrl: '/welcome/online.html',
                controller: 'OnlineCtrl'
            }).
            when('/url', {
                templateUrl: '/welcome/url.html',
                controller: 'UrlCtrl'
            }).
            when('/review', {
                templateUrl: '/welcome/review.html',
                controller: 'ReviewCtrl'
            }).
            when('/done', {
                templateUrl: '/welcome/done.html',
                controller: 'DoneCtrl'
            }).
            otherwise({
                redirectTo: '/'
            });
    }
]);