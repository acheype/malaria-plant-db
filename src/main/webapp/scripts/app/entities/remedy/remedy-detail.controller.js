'use strict';

angular.module('malariaplantdbApp')
    .controller('RemedyDetailController', function ($scope, $rootScope, $stateParams, entity, Remedy, PlantIngredient) {
        $scope.remedy = entity;
        $scope.load = function (id) {
            Remedy.get({id: id}, function(result) {
                $scope.remedy = result;
            });
        };
        var unsubscribe = $rootScope.$on('malariaplantdbApp:remedyUpdate', function(event, result) {
            $scope.remedy = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
