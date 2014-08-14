'use strict'

var welcomeServices = angular.module('welcomeServices', ['ngResource']);

welcomeServices.factory('Initializer', ['$resource',
    function($resource) {
        return $resource('api/init', {}, {
        })
    }
]);
