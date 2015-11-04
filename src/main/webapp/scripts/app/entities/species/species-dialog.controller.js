'use strict';

angular.module('malariaplantdbApp').controller('SpeciesDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Species',
        function($scope, $stateParams, $modalInstance, entity, Species) {

        $scope.species = entity;
        $scope.load = function(id) {
            Species.get({id : id}, function(result) {
                $scope.species = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('malariaplantdbApp:speciesUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.species.id != null) {
                Species.update($scope.species, onSaveFinished);
            } else {
                Species.save($scope.species, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
