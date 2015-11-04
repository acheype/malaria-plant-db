'use strict';

angular.module('malariaplantdbApp').controller('EthnologyDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Ethnology', 'Publication', 'PlantIngredient',
        function($scope, $stateParams, $modalInstance, entity, Ethnology, Publication, PlantIngredient) {

        $scope.ethnology = entity;
        $scope.publications = Publication.sortedQuery();
        $scope.plantIngredients = PlantIngredient.sortedQuery();
        $scope.load = function(id) {
            Ethnology.get({id : id}, function(result) {
                $scope.ethnology = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('malariaplantdbApp:ethnologyUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.ethnology.id != null) {
                Ethnology.update($scope.ethnology, onSaveFinished);
            } else {
                Ethnology.save($scope.ethnology, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
