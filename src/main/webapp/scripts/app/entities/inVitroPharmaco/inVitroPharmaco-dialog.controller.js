'use strict';

angular.module('malariaplantdbApp').controller('InVitroPharmacoDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'InVitroPharmaco', 'Publication', 'Species', 'PiTools',
        function($scope, $stateParams, $uibModalInstance, entity, InVitroPharmaco, Publication, Species, PiTools) {

            $scope.inVitroPharmaco = entity;
            $scope.publications = Publication.sortedQuery();
            $scope.species = Species.sortedQuery();
            $scope.partUsedList = PiTools.partUsedList;

            $scope.curSpecies = null;
            $scope.curPartUsed = null;

            $scope.addPlantIngredient = function(curSpecies, curPartUsed){
                if (curSpecies && curPartUsed &&
                    !PiTools.containsPlantIngredient(curSpecies, curPartUsed, $scope.inVitroPharmaco.remedy.plantIngredients)) {
                    $scope.inVitroPharmaco.remedy.plantIngredients.push({id: null, species: curSpecies, partUsed: curPartUsed});
                    $scope.curSpecies = null;
                    $scope.curPartUsed = null;
                }
            };

            $scope.removePlantIngredient=function(plantIngredient) {
                var index = $scope.inVitroPharmaco.remedy.plantIngredients.indexOf(plantIngredient);
                $scope.inVitroPharmaco.remedy.plantIngredients.splice(index, 1);
            };

            $scope.load = function(id) {
                InVitroPharmaco.get({id : id}, function(result) {
                    $scope.inVitroPharmaco = result;
                });
            };

            var onSaveFinished = function (result) {
                $scope.$emit('malariaplantdbApp:inVitroPharmacoUpdate', result);
                $uibModalInstance.close(result);
            };

            $scope.save = function () {
                if ($scope.inVitroPharmaco.id != null) {
                    InVitroPharmaco.update($scope.inVitroPharmaco, onSaveFinished);
                } else {
                    InVitroPharmaco.save($scope.inVitroPharmaco, onSaveFinished);
                }
            };

            $scope.clear = function() {
                $uibModalInstance.dismiss('cancel');
            }
        }]);

