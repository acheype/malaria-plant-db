'use strict';

angular.module('malariaplantdbApp')
    .controller('CompilerDetailController', function ($scope, $rootScope, $stateParams, entity, Compiler) {
        $scope.compiler = entity;
        $scope.load = function (id) {
            Compiler.get({id: id}, function(result) {
                $scope.compiler = result;
            });
        };
        $rootScope.$on('malariaplantdbApp:compilerUpdate', function(event, result) {
            $scope.compiler = result;
        });
    });
