var welcomeApp = angular.module('dashboardApp', [
    'ngRoute',
    'dashboardControllers',
]);

welcomeApp.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
            when('/', {
            }).
            when('/account', {
            }).
            otherwise({
                redirectTo: '/'
            });
    }
]);