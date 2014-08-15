var dashboardApp = angular.module('dashboardApp', [
    'ngRoute',
    'dashboardControllers',
    'dashboardFilters',
    'dashboardServices'
]);

dashboardApp.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
            when('/device', {
                templateUrl: 'dashboard/device.html',
                controller: 'DeviceCtrl',
                access: { requiredLogin: true }
            }).
            when('/users', {
                templateUrl: 'dashboard/users.html',
                controller: 'UsersCtrl',
                access: { requiredLogin: true }
            }).
            when('/apps', {
                templateUrl: 'dashboard/apps.html',
                controller: 'AppsCtrl',
                access: { requiredLogin: true }
            }).
            when('/store', {
                templateUrl: 'dashboard/store.html',
                controller: 'StoreCtrl',
                access: { requiredLogin: true }
            }).
            when('/support', {
                templateUrl: 'dashboard/support.html',
                controller: 'SupportCtrl',
                access: { requiredLogin: true }
            }).
            when('/login', {
                 templateUrl: 'dashboard/login.html',
                 controller: 'LoginCtrl',
                 access: { requiredLogin: false }
            }).
            otherwise({
                redirectTo: '/device'
            });
    }
]);

dashboardApp.constant('API_BASE', 'api/');

dashboardApp.factory('Session', ['$window', function ($window) {
    var session = {
        username: undefined,
        token: undefined,
        saveSession: function () {
            $window.sessionStorage.session = JSON.stringify(this);
        },
        loadSession: function () {
            var session_data = $window.sessionStorage.session;
            if (session_data) {
                var data = JSON.parse(session_data);
                this.username = data.username;
                this.token = data.token;
            }
        }
    };
    return session;
}])

// TODO move login to separate page
//dashboardApp.run(function($rootScope, $location, Session) {
//       $rootScope.$on("$routeChangeStart", function(event, nextRoute, currentRoute) {
//           Session.loadSession();
//           if (nextRoute.access.requiredLogin && !Session.token) {
//               $location.path("login");
//           }
//       });
//   });
