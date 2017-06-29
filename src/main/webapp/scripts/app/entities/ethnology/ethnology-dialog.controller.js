'use strict';

angular.module('malariaplantdbApp').controller('EthnologyDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Ethnology', 'Publication', 'Species',
        function($scope, $stateParams, $uibModalInstance, entity, Ethnology, Publication, Species) {

        $scope.ethnology = entity;
        $scope.publications = Publication.sortedQuery();
        $scope.species = Species.sortedQuery();
        $scope.partUsedList = ['Aerial part', 'Aerial part with flower', 'Aerial part with fruit', 'Bark', 'Bulb',
            'Essential oil', 'Exsudate', 'Flower', 'Flower bud', 'Fruit', 'Heart wood', 'Inner bark', 'Latex', 'Leaf',
            'Leaf bud', 'Leaf petiole', 'Other', 'Part not defined', 'Petal', 'Rhizom', 'Root', 'Root bark', 'Sap',
            'Seed', 'Stem', 'Stem and leaf', 'Stem bark', 'Tendril', 'Whole plant'];

        $scope.curSpecies = null;
        $scope.curPartUsed = null;

        $scope.containsPlantIngredient = function(species, partUsed, list) {
            return list.filter(function(listItem) {
                    return angular.equals(listItem.species, species) &&
                        angular.equals(listItem.partUsed, partUsed)
                }).length > 0;
        };


        $scope.addPlantIngredient = function(curSpecies, curPartUsed){
            if (curSpecies && curPartUsed &&
                    !$scope.containsPlantIngredient(curSpecies, curPartUsed, $scope.ethnology.remedy.plantIngredients)) {
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
