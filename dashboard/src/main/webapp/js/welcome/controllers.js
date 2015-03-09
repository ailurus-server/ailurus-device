'use strict'

var welcomeControllers = angular.module('welcomeControllers', []);

welcomeControllers.controller('AppCtrl', ['$scope',
    function ($scope) {
        $scope.app = {
            title: 'Welcome',
            step: 0,
            maxStep: 0,
            done: false,

            setStep: function(step) {
                $scope.app.step = step;
                $scope.app.maxStep = Math.max($scope.app.maxStep, step);
            },

            getStepClass: function(stepNum) {
                if (stepNum == $scope.app.step) {
                    return 'btn-primary';
                } else if (stepNum <= $scope.app.maxStep) {
                    return 'btn-default';
                } else {
                    return 'disabled';
                }
            }
        };

        $scope.data = {
            username: '',
            email: '',
            password: '',
            confirmation: '',
            hostname: '',
            online: undefined,
            urlType: 'ailurus',
            subdomain: '',
            url: ''
        };
    }
]);

welcomeControllers.controller('WelcomeCtrl', ['$scope',
    function ($scope) {
        $scope.app.title = 'Welcome';
        $scope.app.setStep(0);
    }
]);

welcomeControllers.controller('UserCtrl', ['$scope',
    function ($scope) {
        $scope.app.title = 'Add Your First User';
        $scope.app.setStep(1);

        $scope.$on('$locationChangeStart', function(event) {
            if ($scope.userForm.$invalid) {
                event.preventDefault();
            }
        });
    }
]);

welcomeControllers.controller('NameCtrl', ['$scope',
    function ($scope) {
        $scope.app.title = 'Name Your Device';
        $scope.app.setStep(2);

        $scope.$on('$locationChangeStart', function(event) {
            if ($scope.hostForm.$invalid) {
                event.preventDefault();
            }
        });
    }
]);

welcomeControllers.controller('OnlineCtrl', ['$scope',
    function ($scope) {
        $scope.app.title = 'Get Your Device Online';
        $scope.app.setStep(3);
        $scope.setOnline = function(isOnline) {
            $scope.data.online = isOnline;
        };

        $scope.$on('$locationChangeStart', function(event) {
            if ($scope.data.online === undefined) {
                event.preventDefault();
            }
        });
    }
]);

welcomeControllers.controller('UrlCtrl', ['$scope', '$http', 'API_BASE',
    function ($scope, $http, API_BASE) {
        $scope.app.title = 'Set a URL';
        $scope.app.setStep(4);

        $scope.selectUrlType = function(urlType) {
            $scope.data.urlType = urlType;
        };

        $scope.updateUrl = function(url) {
            if ($scope.ailurusForm.subdomain.$valid) {
                $scope.ailurusForm.subdomain.$updating = true;
                $http.get(API_BASE + 'network/dns/' + url)
                .success(function(result) {
                    if (result) {
                        $scope.ailurusForm.subdomain.$taken = true;
                        $scope.data.subdomain = "";
                    } else {
                        $scope.ailurusForm.subdomain.$taken = false;
                        $scope.data.subdomain = url;
                    }
                    $scope.ailurusForm.subdomain.$updating = false;
                })
                .error(function(result) {
                    // TODO display the error somewhere
                    $scope.ailurusForm.subdomain.$updating = false;
                    console.log(result);
                });
            }
        }

        $scope.canContinue = function() {
            switch ($scope.data.urlType) {
                case 'ailurus':
                    return !!$scope.data.subdomain &&
                     !$scope.ailurusForm.subdomain.$updating &&
                     $scope.ailurusForm.subdomain.$valid;
                case 'own':
                    return !!$scope.data.url;
                case 'none':
                    return true;
                default:
                    return false;
            }
        };

        $scope.$on('$locationChangeStart', function(event, current, prev) {
            if (!$scope.canContinue()) {
                if (current.endsWith('/online')) {
                    $scope.maxStep = 3;
                    return;
                }
                event.preventDefault();
            }
        });
    }
]);

welcomeControllers.controller('ReviewCtrl', [
    '$scope', '$location', '$http', 'AILURUS_DOMAIN', 'API_BASE',
    function ($scope, $location, $http, AILURUS_DOMAIN, API_BASE) {
        $scope.app.title = 'Review Your Changes';
        $scope.app.setStep(5);
        $scope.getUrl = function() {
            switch ($scope.data.urlType) {
                case 'ailurus':
                    return $scope.data.subdomain + '.' + AILURUS_DOMAIN;
                case 'own':
                    return $scope.data.url ;
                case 'none':
                    return null;
                default:
                    // TODO display IP address
                    return undefined;
            }
        };
        $scope.submit = function() {
            $http.post(API_BASE + 'device/init', {
               username: $scope.data.username,
               email: $scope.data.email,
               password: $scope.data.password,
               hostname: $scope.data.hostname,
               url: $scope.data.online ? $scope.getUrl() : null
            }).success(function(result, status, headers, config) {
                $location.path('done');
            }).error(function(result, status, headers, config) {
                // TODO display the error somewhere
                console.log(result);
            });
        }
    }
]);

welcomeControllers.controller('DoneCtrl', ['$scope', '$location',
    function ($scope, $location) {
        $scope.app.title = 'Done Configurations';
        $scope.app.done = true;
    }
]);
