'use strict'

var dashboardServices = angular.module('dashboardServices', ['ngResource']);

dashboardServices.factory('Api',
    ['$resource', '$http', 'API_BASE',
    function($resource, $http, API_BASE) {
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
            },

            post: function(url, data) {
                return $http.post(API_BASE + url, data);
            },

            put: function(url, data) {
                return $http.put(API_BASE + url, data);
            },

            'delete': function(url, data) {
                return $http.delete(API_BASE + url, data);
            },
        };
    }
]);