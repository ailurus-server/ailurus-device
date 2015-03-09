var dashboardApp = angular.module('dashboardApp', [
    'ngRoute',
    'dashboardControllers',
    'dashboardFilters',
    'dashboardServices'
]);

dashboardApp.constant('API_BASE', 'api/');

dashboardApp.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
            when('/device', {
                templateUrl: 'html/dashboard/device.html',
                controller: 'DeviceCtrl',
                access: { requiredLogin: true }
            }).
            when('/users', {
                templateUrl: 'html/dashboard/users.html',
                controller: 'UsersCtrl',
                access: { requiredLogin: true }
            }).
            when('/apps', {
                templateUrl: 'html/dashboard/apps.html',
                controller: 'AppsCtrl',
                access: { requiredLogin: true }
            }).
            when('/store', {
                templateUrl: 'html/dashboard/store.html',
                controller: 'StoreCtrl',
                access: { requiredLogin: true }
            }).
            when('/store/search', {
                templateUrl: 'html/dashboard/search.html',
                controller: 'SearchCtrl',
                access: { requiredLogin: true }
            }).
            when('/store/search/:keyword', {
                templateUrl: 'html/dashboard/search.html',
                controller: 'SearchCtrl',
                access: { requiredLogin: true }
            }).
            when('/support', {
                templateUrl: 'html/dashboard/support.html',
                controller: 'SupportCtrl',
                access: { requiredLogin: true }
            }).
            when('/login', {
                 templateUrl: 'html/dashboard/login.html',
                 controller: 'LoginCtrl',
                 access: { requiredLogin: false }
            }).
            otherwise({
                redirectTo: '/device',
                access: { requiredLogin: false }
            });
    }
]);

dashboardApp.factory('Session', ['$window', function ($window) {
    var session = {
        username: null,
        token: null,
        email: null,

        isLoaded: function() {
            return this.username != null && this.token != null && this.email != null;
        },

        saveSession: function () {
            $window.sessionStorage.session = JSON.stringify(this);
        },

        clearSession: function() {
            this.username = null;
            this.token = null;
            this.email = null;
            this.saveSession();
        },

        loadSession: function () {
            var session_data = $window.sessionStorage.session;
            if (session_data) {
                var data = JSON.parse(session_data);
                this.username = data.username;
                this.token = data.token;
                this.email = data.email;
            }
        }
    };
    return session;
}])

// TODO move login to separate page
dashboardApp.run(function($rootScope, $location, Session) {
       $rootScope.$on("$routeChangeStart", function(event, nextRoute, currentRoute) {
           Session.loadSession();
           if (nextRoute.access.requiredLogin && !Session.isLoaded()) {
               $location.path("login");
           }
       });
   });
