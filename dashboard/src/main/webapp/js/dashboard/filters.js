'use strict'

var dashboardFilters = angular.module('dashboardFilters', []);

dashboardFilters.filter('bytes',
    function() {
        return function(amount) {
            if (amount === undefined || amount === null) {
                return amount;
            }

            var units = ['B', 'KB', 'MB', 'GB', 'TB'];
            var scale = 1;
            for (var i = 0; i < units.length; ++i) {
                scale *= 1000;
                if (amount < scale) {
                    var digits = +(Math.round(amount * 1000 / scale + "e+2")  + "e-2");
                    return digits + ' ' + units[i];
                }
            }

            var digits = +(Math.round(amount * 1000 / scale + "e+2")  + "e-2");
            return digits + ' ' + units[units.length - 1];
        };
    }
);

dashboardFilters.filter('hz',
    function() {
        return function(amount) {
            if (amount === undefined || amount === null) {
                return amount;
            }

            var units = ['Hz', 'kHz', 'MHz', 'GHz'];
            var scale = 1;
            for (var i = 0; i < units.length; ++i) {
                scale *= 1000;
                if (amount < scale) {
                    var digits = +(Math.round(amount * 1000 / scale + "e+2")  + "e-2");
                    return digits + ' ' + units[i];
                }
            }

            var digits = +(Math.round(amount * 1000 / scale + "e+2")  + "e-2");
            return digits + ' ' + units[units.length - 1];
        };
    }
);

dashboardApp.filter('percentUsed', function() {
    return function(resource) {
        if (resource === undefined || resource === null) {
            return resource;
        }

        return Math.round(100 * resource.used / resource.total);
    };
});

dashboardApp.filter('urlencode', function() {
    return function(keyword) {
        if (keyword === undefined || keyword === null) {
            return keyword;
        }

        return encodeURIComponent(keyword);
    };
});