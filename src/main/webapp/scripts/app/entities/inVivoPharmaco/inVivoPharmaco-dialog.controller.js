'use strict';

angular.module('malariaplantdbApp').controller('InVivoPharmacoDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'InVivoPharmaco', 'Publication', 'PlantIngredient',
        function($scope, $stateParams, $modalInstance, entity, InVivoPharmaco, Publication, PlantIngredient) {

        $scope.inVivoPharmaco = entity;
        $scope.publications = Publication.sortedQuery();
        $scope.plantIngredients = PlantIngredient.sortedQuery();
        $scope.load = function(id) {
            InVivoPharmaco.get({id : id}, function(result) {
                $scope.inVivoPharmaco = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('malariaplantdbApp:inVivoPharmacoUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.inVivoPharmaco.id != null) {
                InVivoPharmaco.update($scope.inVivoPharmaco, onSaveFinished);
            } else {
                InVivoPharmaco.save($scope.inVivoPharmaco, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
