'use strict';

angular.module('portfolioApp')
    .controller('QuicksortController', function ($scope, Quicksort) {
        $scope.quicksorts = [];
        $scope.loadAll = function() {
            Quicksort.query(function(result) {
               $scope.quicksorts = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Quicksort.get({id: id}, function(result) {
                $scope.quicksort = result;
                $('#deleteQuicksortConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Quicksort.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteQuicksortConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.quicksort = {unsorted: null, sorted: null, executionTime: null, id: null};
        };
    });
