'use strict';

angular.module('malariaplantdbApp').controller('InVivoPharmacoDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'InVivoPharmaco', 'Publication', 'Species', 'PiTools',
        function($scope, $stateParams, $uibModalInstance, entity, InVivoPharmaco, Publication, Species, PiTools) {

            $scope.inVivoPharmaco = entity;
            $scope.publications = Publication.sortedQuery();
            $scope.species = Species.sortedQuery();
            $scope.partUsedList = PiTools.partUsedList;

            $scope.curSpecies = null;
            $scope.curPartUsed = null;

            $scope.addPlantIngredient = function(curSpecies, curPartUsed){
                if (curSpecies && curPartUsed &&
                    !PiTools.containsPlantIngredient(curSpecies, curPartUsed, $scope.inVivoPharmaco.remedy.plantIngredients)) {
                    $scope.inVivoPharmaco.remedy.plantIngredients.push({id: null, species: curSpecies, partUsed: curPartUsed});
                    $scope.curSpecies = null;
                    $scope.curPartUsed = null;
                }
            };

            $scope.removePlantIngredient=function(plantIngredient) {
                var index = $scope.inVivoPharmaco.remedy.plantIngredients.indexOf(plantIngredient);
                $scope.inVivoPharmaco.remedy.plantIngredients.splice(index, 1);
            };

            $scope.load = function(id) {
                InVivoPharmaco.get({id : id}, function(result) {
                    $scope.inVivoPharmaco = result;
                });
            };

            var onSaveFinished = function (result) {
                $scope.$emit('malariaplantdbApp:inVivoPharmacoUpdate', result);
                $uibModalInstance.close(result);
            };

            $scope.save = function () {
                if ($scope.inVivoPharmaco.id != null) {
                    InVivoPharmaco.update($scope.inVivoPharmaco, onSaveFinished);
                } else {
                    InVivoPharmaco.save($scope.inVivoPharmaco, onSaveFinished);
                }
            };

            $scope.clear = function() {
                $uibModalInstance.dismiss('cancel');
            };
        }]);
