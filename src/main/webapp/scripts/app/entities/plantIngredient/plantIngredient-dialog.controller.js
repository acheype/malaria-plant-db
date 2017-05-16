'use strict';

angular.module('malariaplantdbApp').controller('PlantIngredientDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'PlantIngredient', 'Species',
        function($scope, $stateParams, $uibModalInstance, entity, PlantIngredient, Species) {

        $scope.plantIngredient = entity;
        $scope.speciesList = Species.sortedQuery();
        $scope.load = function(id) {
            PlantIngredient.get({id : id}, function(result) {
                $scope.plantIngredient = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('malariaplantdbApp:plantIngredientUpdate', result);
            $uibModalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.plantIngredient.id != null) {
                PlantIngredient.update($scope.plantIngredient, onSaveFinished);
            } else {
                PlantIngredient.save($scope.plantIngredient, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
