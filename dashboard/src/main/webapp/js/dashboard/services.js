'use strict'

var dashboardServices = angular.module('dashboardServices', ['ngResource']);

dashboardServices.factory('Api', ['$resource', 'API_BASE',
    function($resource, API_BASE) {
        return {
            queryOne: function(url, params) {
                params = params || {};
                return $resource(API_BASE + url, params, {
                    query: {method: 'GET'}
                }).query();
            },
            queryMany: function(url, params) {
                params = params || {};
                return $resource(API_BASE + url, params, {
                    query: {method: 'GET', isArray: true}
                }).query();
            }
        };
    }
]);