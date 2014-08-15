'use strict'

var dashboardServices = angular.module('dashboardServices', ['ngResource']);

dashboardServices.factory('Device', ['$resource', 'API_BASE',
    function($resource, API_BASE) {
        return {
            query: function() {
                return $resource(API_BASE + 'device', {}, {
                    query: {method: 'GET'}
                }).query();
            }
        };
    }
]);