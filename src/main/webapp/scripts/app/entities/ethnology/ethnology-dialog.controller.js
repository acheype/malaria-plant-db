'use strict';

angular.module('malariaplantdbApp').controller('EthnologyDialogController',
    ['$scope', '$rootScope', '$stateParams', '$uibModalInstance', 'entity', 'Ethnology', 'Publication', 'Species',
        'Pitools', function($scope, $rootScope, $stateParams, $uibModalInstance, entity, Ethnology, Publication,
                            Species, PiTools) {

        $scope.ethnology = entity;
        $scope.publications = Publication.sortedQuery();
        $scope.species = Species.sortedQuery();
        $scope.partUsedList = PiTools.partUsedList;

        $scope.curSpecies = null;
        $scope.curPartUsed = null;

        $scope.addPlantIngredient = function(curSpecies, curPartUsed){
            if (curSpecies && curPartUsed &&
                    !PiTools.containsPlantIngredient(curSpecies, curPartUsed, $scope.ethnology.remedy.plantIngredients)) {
                $scope.ethnology.remedy.plantIngredients.push({id: null, species: curSpecies, partUsed: curPartUsed});
                $scope.curSpecies = null;
                $scope.curPartUsed = null;
            }
        };

        $scope.removePlantIngredient=function(plantIngredient) {
            var index = $scope.ethnology.remedy.plantIngredients.indexOf(plantIngredient);
            $scope.ethnology.remedy.plantIngredients.splice(index, 1);
        };

        $scope.load = function(id) {
            Ethnology.get({id : id}, function(result) {
                $scope.ethnology = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('malariaplantdbApp:ethnologyUpdate', result);
            $uibModalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.ethnology.id != null) {
                Ethnology.update($scope.ethnology, onSaveFinished);
            } else {
                Ethnology.save($scope.ethnology, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
