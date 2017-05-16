'use strict';

angular.module('malariaplantdbApp').controller('SpeciesDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Species',
        function($scope, $stateParams, $uibModalInstance, entity, Species) {

        $scope.species = entity;
        $scope.load = function(id) {
            Species.get({id : id}, function(result) {
                $scope.species = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('malariaplantdbApp:speciesUpdate', result);
            $uibModalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.species.id != null) {
                Species.update($scope.species, onSaveFinished);
            } else {
                Species.save($scope.species, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
