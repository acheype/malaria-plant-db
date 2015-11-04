'use strict';

angular.module('malariaplantdbApp')
    .controller('PubSpeciesDetailController', function ($scope, $rootScope, $stateParams, entity, PubSpecies, Publication) {
        $scope.pubSpecies = entity;
        $scope.load = function (id) {
            PubSpecies.get({id: id}, function(result) {
                $scope.pubSpecies = result;
            });
        };
        $rootScope.$on('malariaplantdbApp:pubSpeciesUpdate', function(event, result) {
            $scope.pubSpecies = result;
        });
    });
