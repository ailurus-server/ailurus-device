var welcomeApp = angular.module('welcomeApp', [
    'ngRoute',
    'welcomeControllers',
]);

welcomeApp.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
            when('/', {
                templateUrl: 'welcome/welcome.html',
                controller: 'WelcomeCtrl'
            }).
            when('/account', {
                templateUrl: 'welcome/account.html',
                controller: 'AccountCtrl'
            }).
            otherwise({
                redirectTo: '/'
            });
    }
]);