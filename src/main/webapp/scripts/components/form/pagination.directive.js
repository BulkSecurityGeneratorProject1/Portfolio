/* globals $ */
'use strict';

angular.module('portfolioApp')
    .directive('portfolioAppPagination', function() {
        return {
            templateUrl: 'scripts/components/form/pagination.html'
        };
    });
