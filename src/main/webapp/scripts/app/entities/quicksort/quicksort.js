'use strict';

angular.module('portfolioApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('quicksort', {
                parent: 'entity',
                url: '/quicksorts',
                data: {
                    roles: [],
                    pageTitle: 'Quicksorts'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/quicksort/quicksorts.html',
                        controller: 'QuicksortController'
                    }
                },
                resolve: {
                }
            })
            .state('quicksort.detail', {
                parent: 'entity',
                url: '/quicksort/{id}',
                data: {
                    roles: [],
                    pageTitle: 'Quicksort'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/quicksort/quicksort-detail.html',
                        controller: 'QuicksortDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Quicksort', function($stateParams, Quicksort) {
                        return Quicksort.get({id : $stateParams.id});
                    }]
                }
            })
            .state('quicksort.new', {
                parent: 'quicksort',
                url: '/new',
                data: {
                    roles: []
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/quicksort/quicksort-dialog.html',
                        controller: 'QuicksortDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {unsorted: null, sorted: null, executionTime: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('quicksort', null, { reload: true });
                    }, function() {
                        $state.go('quicksort');
                    })
                }]
            })
            .state('quicksort.edit', {
                parent: 'quicksort',
                url: '/{id}/edit',
                data: {
                    roles: []
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/quicksort/quicksort-dialog.html',
                        controller: 'QuicksortDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Quicksort', function(Quicksort) {
                                return Quicksort.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('quicksort', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
