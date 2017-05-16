'use strict';

angular.module('malariaplantdbApp').controller('InVitroPharmacoDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'InVitroPharmaco', 'Publication', 'PlantIngredient',
        function($scope, $stateParams, $uibModalInstance, entity, InVitroPharmaco, Publication, PlantIngredient) {

            $scope.inVitroPharmaco = entity;
            $scope.publications = Publication.sortedQuery();
            $scope.plantIngredients = PlantIngredient.sortedQuery();
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

