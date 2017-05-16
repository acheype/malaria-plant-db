'use strict';

angular.module('malariaplantdbApp').controller('PubSpeciesDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Species', 'PubSpecies', 'Publication',
        function($scope, $stateParams, $uibModalInstance, entity, Species, PubSpecies, Publication) {

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
            $uibModalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.pubSpecies.id != null) {
                PubSpecies.update($scope.pubSpecies, onSaveFinished);
            } else {
                PubSpecies.save($scope.pubSpecies, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
