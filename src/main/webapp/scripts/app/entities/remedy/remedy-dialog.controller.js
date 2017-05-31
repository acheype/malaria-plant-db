'use strict';

angular.module('malariaplantdbApp').controller('RemedyDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Remedy', 'PlantIngredient',
        function($scope, $stateParams, $modalInstance, entity, Remedy, PlantIngredient) {

        $scope.remedy = entity;
        $scope.plantingredients = PlantIngredient.query();
        $scope.load = function(id) {
            Remedy.get({id : id}, function(result) {
                $scope.remedy = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('malariaplantdbApp:remedyUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.remedy.id != null) {
                Remedy.update($scope.remedy, onSaveFinished);
            } else {
                Remedy.save($scope.remedy, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
