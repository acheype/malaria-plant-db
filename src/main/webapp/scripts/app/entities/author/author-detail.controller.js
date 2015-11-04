'use strict';

angular.module('malariaplantdbApp')
    .controller('AuthorDetailController', function ($scope, $rootScope, $stateParams, entity, Author, Publication) {
        $scope.author = entity;
        $scope.load = function (id) {
            Author.get({id: id}, function(result) {
                $scope.author = result;
            });
        };
        $rootScope.$on('malariaplantdbApp:authorUpdate', function(event, result) {
            $scope.author = result;
        });
    });
