'use strict';

angular.module('malariaplantdbApp')
    .controller('DatabaseDetailController', function ($scope, $rootScope, $stateParams, publication, ethnology,
                                                      pubSpecies, inVivoPharmacos, inVitroPharmacos, pubSummary) {
        $scope.publication = publication;
        $scope.pubSpecies = pubSpecies;
        $scope.ethnology = ethnology;
        $scope.inVivoPharmacos = inVivoPharmacos;
        $scope.inVitroPharmacos = inVitroPharmacos;

        angular.forEach(pubSummary.plantIngredients, function(key, value){
            if (angular.equals(value.plantIngredientsIds, $stateParams.piIds)) {
               delete pubSummary.plantIngredients[key];
            }
        });
        $scope.pubSummary = pubSummary.plantIngredients;

        var plantIngredients = ethnology ? ethnology.plantIngredients :
            (inVivoPharmacos.length > 0 ? inVivoPharmacos[0].plantIngredients :
                (inVitroPharmacos.length > 0 ? inVitroPharmacos[0].plantIngredients : null));

        $scope.plantIngredientsStr = plantIngredients && plantIngredients.length > 0 ?
            plantIngredients.map(
                function(o) {return o.species.family + ' ' + o.species.species + ', ' + o.partUsed}
            ).join(" / ") :
            pubSpecies[0].species.family + " " + pubSpecies[0].species.species;
    });
