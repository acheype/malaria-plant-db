'use strict';

angular.module('malariaplantdbApp')
    .controller('DatabaseDetailController', function ($scope, $rootScope, $stateParams, publication, ethnology,
                                                      pubSpecies, inVivoPharmacos, inVitroPharmacos, pubSummary) {
        $scope.publication = publication;
        $scope.pubSpecies = pubSpecies;
        $scope.ethnology = ethnology;
        $scope.inVivoPharmacos = inVivoPharmacos;
        $scope.inVitroPharmacos = inVitroPharmacos;
        $scope.otherRemedies = {};

        var paramPiIds = $stateParams.piIds.split(",").map(Number);

        angular.forEach(pubSummary.plantIngredients, function(value, key){
            if (angular.equals(value.plantIngredientsIds, paramPiIds)) {
                $scope.plantIngredientsStr = key;
            } else {
                $scope.otherRemedies[key] = value;
            }
        });
    });
