'use strict';

angular.module('portfolioApp')
    .controller('QuicksortDetailController', function ($scope, $rootScope, $stateParams, entity, Quicksort) {
        $scope.quicksort = entity;
        $scope.load = function (id) {
            Quicksort.get({id: id}, function(result) {
                $scope.quicksort = result;
            });
        };
        $rootScope.$on('portfolioApp:quicksortUpdate', function(event, result) {
            $scope.quicksort = result;
        });
    });
