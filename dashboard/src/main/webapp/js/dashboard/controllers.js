var dashboardControllers = angular.module('dashboardControllers', []);

dashboardControllers.controller('AppCtrl', ['$scope',
    function ($scope) {
        $scope.app = new Dashboard();
    }
]);

dashboardControllers.controller('LoginCtrl', ['cfg', '$scope', '$http', '$location', 'Session',
    function (cfg, $scope, $http, $location, Session) {
        $scope.alert = {
            type: 'danger',
            msg: ''
        };

        $scope.login = function() {
            if (!($scope.cred.username && $scope.cred.password)) {
                return;
            }
            $http({
                url: cfg.api_base + 'accounts/' + $scope.cred.username + '/login',
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