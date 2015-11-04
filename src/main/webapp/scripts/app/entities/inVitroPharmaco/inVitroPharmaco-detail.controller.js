'use strict';

angular.module('malariaplantdbApp')
    .controller('InVitroPharmacoDetailController', function ($scope, $rootScope, $stateParams, entity, InVitroPharmaco, Publication, PlantIngredient) {
        $scope.inVitroPharmaco = entity;
        $scope.load = function (id) {
            InVitroPharmaco.get({id: id}, function(result) {
                $scope.inVitroPharmaco = result;
            });
        };
        $rootScope.$on('malariaplantdbApp:inVitroPharmacoUpdate', function(event, result) {
            $scope.inVitroPharmaco = result;
        });
    });
