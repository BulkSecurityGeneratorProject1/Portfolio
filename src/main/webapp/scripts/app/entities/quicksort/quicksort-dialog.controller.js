'use strict';

angular.module('portfolioApp').controller('QuicksortDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Quicksort',
        function($scope, $stateParams, $modalInstance, entity, Quicksort) {

        $scope.quicksort = entity;
        $scope.load = function(id) {
            Quicksort.get({id : id}, function(result) {
                $scope.quicksort = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('portfolioApp:quicksortUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.quicksort.id != null) {
                Quicksort.update($scope.quicksort, onSaveFinished);
            } else {
                Quicksort.save($scope.quicksort, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
