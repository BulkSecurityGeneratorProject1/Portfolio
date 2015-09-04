/* globals $ */
'use strict';

angular.module('portfolioApp')
    .directive('portfolioAppPager', function() {
        return {
            templateUrl: 'scripts/components/form/pager.html'
        };
    });
