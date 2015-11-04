'use strict';

angular.module('malariaplantdbApp')
    .controller('PlantIngredientDetailController', function ($scope, $rootScope, $stateParams, entity, PlantIngredient, Species) {
        $scope.plantIngredient = entity;
        $scope.load = function (id) {
            PlantIngredient.get({id: id}, function(result) {
                $scope.plantIngredient = result;
            });
        };
        $rootScope.$on('malariaplantdbApp:plantIngredientUpdate', function(event, result) {
            $scope.plantIngredient = result;
        });
    });
