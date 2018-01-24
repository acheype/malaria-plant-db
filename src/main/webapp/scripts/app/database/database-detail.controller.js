'use strict';

angular.module('malariaplantdbApp')
    .controller('DatabaseDetailController', function ($scope, $rootScope, $stateParams, publication, pubSpecies,
                                                      ethnology, inVivoPharmacos, inVitroPharmacos, pubSummary) {
        $scope.publication = publication;
        $scope.pubSpecies = pubSpecies;
        $scope.ethnology = ethnology;
        $scope.inVivoPharmacos = inVivoPharmacos;
        $scope.inVitroPharmacos = inVitroPharmacos;
        $scope.otherRemedies = {};

        angular.forEach(pubSummary.remedies, function(value, key){
            if (value.remedyId == $stateParams.remId) {
                $scope.remedyStr = key;
            } else {
                $scope.otherRemedies[key] = value;
            }
        });
    });
