'use strict';

angular.module('portfolioApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('gravitux', {
                parent: 'entity',
                url: '/gravitux',
                data: {
                    roles: [],
                    pageTitle: 'GraviTux'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/gravitux/gravituxs.html',
                        controller: 'GravituxController'
                    }
                },
                resolve: {
                }
            })
    });
