'use strict';

angular.module('malariaplantdbApp')
    .controller('SpeciesDetailController', function ($scope, $rootScope, $stateParams, entity, Species) {
        $scope.species = entity;
        $scope.load = function (id) {
            Species.get({id: id}, function(result) {
                $scope.species = result;
            });
        };
        $rootScope.$on('malariaplantdbApp:speciesUpdate', function(event, result) {
            $scope.species = result;
        });
    });
