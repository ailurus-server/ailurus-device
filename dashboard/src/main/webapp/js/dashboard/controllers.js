var dashboardControllers = angular.module('dashboardControllers', []);

dashboardControllers.controller('AppCtrl', ['$scope',
    function ($scope) {
        $scope.app = new Dashboard();
    }
]);