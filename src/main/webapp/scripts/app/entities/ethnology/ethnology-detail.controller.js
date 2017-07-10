'use strict';

angular.module('malariaplantdbApp')
    .controller('EthnologyDetailController', function ($scope, $rootScope, $stateParams, entity, Ethnology) {
        $scope.ethnology = entity;
        $scope.load = function (id) {
            Ethnology.get({id: id}, function(result) {
                $scope.ethnology = result;
            });
        };
        $rootScope.$on('malariaplantdbApp:ethnologyUpdate', function(event, result) {
            $scope.ethnology = result;
        });
    });
