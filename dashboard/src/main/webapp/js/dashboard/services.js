'use strict'

var dashboardServices = angular.module('dashboardServices', ['ngResource']);

dashboardServices.factory('Api', ['$resource', 'API_BASE',
    function($resource, API_BASE) {
        return {
            query: function(url) {
                return $resource(API_BASE + url, {}, {
                    query: {method: 'GET'}
                }).query();
            }
        };
    }
]);