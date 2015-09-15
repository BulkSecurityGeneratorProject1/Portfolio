'use strict';

angular.module('portfolioApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('gameOfLife', {
                parent: 'entity',
                url: '/gameOfLifes',
                data: {
                    roles: [],
                    pageTitle: 'GameOfLifes'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/gameOfLife/gameOfLifes.html',
                        controller: 'GameOfLifeController'
                    }
                },
                resolve: {}
            });
    });
