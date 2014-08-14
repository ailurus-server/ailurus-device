var welcomeApp = angular.module('dashboardApp',
    ['ngRoute', 'dashboardControllers']
);

welcomeApp.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: '/dashboard/dashboard.html',
                controller: 'AppCtrl',
                access: { requiredLogin: true }
            })
            .when('/login', {
                templateUrl: '/dashboard/login.html',
                controller: 'LoginCtrl',
                access: { requiredLogin: false }
            })
            .otherwise({
                redirectTo: '/'
            });
    }
]);

welcomeApp.constant('cfg', {
    api_base: 'http://localhost:8080/api/',
});

welcomeApp.factory('Session', ['$window', function ($window) {
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

welcomeApp.run(function($rootScope, $location, Session) {
    $rootScope.$on("$routeChangeStart", function(event, nextRoute, currentRoute) {
        Session.loadSession();
        if (nextRoute.access.requiredLogin && !Session.token) {
            $location.path("login");
        }
    });
});
