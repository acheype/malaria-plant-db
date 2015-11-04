'use strict';

angular.module('malariaplantdbApp').controller('PubSpeciesDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Species', 'PubSpecies', 'Publication',
        function($scope, $stateParams, $modalInstance, entity, Species, PubSpecies, Publication) {

        $scope.pubSpecies = entity;
        $scope.publications = Publication.sortedQuery();
        $scope.speciesList = Species.sortedQuery();
        $scope.load = function(id) {
            PubSpecies.get({id : id}, function(result) {
                $scope.pubSpecies = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('malariaplantdbApp:pubSpeciesUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.pubSpecies.id != null) {
                PubSpecies.update($scope.pubSpecies, onSaveFinished);
            } else {
                PubSpecies.save($scope.pubSpecies, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
