var welcomeControllers = angular.module('welcomeControllers', []);

welcomeControllers.controller('WelcomeCtrl', ['$scope',
    function ($scope) {
    }
]);

welcomeControllers.controller('AccountCtrl', ['$scope',
    function ($scope) {
        $scope.username = '';
        $scope.email = '';
        $scope.password = '';
        $scope.confirm = '';
    }
]);
