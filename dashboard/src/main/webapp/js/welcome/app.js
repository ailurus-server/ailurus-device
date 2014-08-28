'use strict'

var welcomeApp = angular.module('welcomeApp', [
    'ngRoute',
    'welcomeControllers'
]);

welcomeApp.constant('AILURUS_DOMAIN', 'ailurus.ca');
welcomeApp.constant('API_BASE', 'api/');

welcomeApp.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
            when('/', {
                templateUrl: 'html/welcome/welcome.html',
                controller: 'WelcomeCtrl'
            }).
            when('/user', {
                templateUrl: 'html/welcome/user.html',
                controller: 'UserCtrl'
            }).
            when('/name', {
                templateUrl: 'html/welcome/name.html',
                controller: 'NameCtrl'
            }).
            when('/online', {
                templateUrl: 'html/welcome/online.html',
                controller: 'OnlineCtrl'
            }).
            when('/url', {
                templateUrl: 'html/welcome/url.html',
                controller: 'UrlCtrl'
            }).
            when('/review', {
                templateUrl: 'html/welcome/review.html',
                controller: 'ReviewCtrl'
            }).
            when('/done', {
                templateUrl: 'html/welcome/done.html',
                controller: 'DoneCtrl'
            }).
            otherwise({
                redirectTo: '/'
            });
    }
]);