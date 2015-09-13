'use strict';

angular.module('portfolioApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('lifeSimulation', {
                parent: 'entity',
                url: '/lifeSimulations',
                data: {
                    roles: [],
                    pageTitle: 'LifeSimulations'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/lifeSimulation/lifeSimulations.html',
                        controller: 'LifeSimulationController'
                    }
                },
                resolve: {}
            })
    });
