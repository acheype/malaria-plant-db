'use strict';

angular.module('malariaplantdbApp')
    .controller('InVivoPharmacoDetailController', function ($scope, $rootScope, $stateParams, entity, InVivoPharmaco, Publication, PlantIngredient) {
        $scope.inVivoPharmaco = entity;
        $scope.load = function (id) {
            InVivoPharmaco.get({id: id}, function(result) {
                $scope.inVivoPharmaco = result;
            });
        };
        $rootScope.$on('malariaplantdbApp:inVivoPharmacoUpdate', function(event, result) {
            $scope.inVivoPharmaco = result;
        });
    });
