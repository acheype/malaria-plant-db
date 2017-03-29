'use strict';

angular.module('malariaplantdbApp')
    .controller('DatabaseDetailController', function ($scope, $rootScope, $stateParams, entity, Publication) {
        $scope.publication = entity;
        $scope.load = function (id) {
            Publication.get({id: id}, function(result) {
                $scope.publication = result;
            });
        }
            .$promise.then(function(data){
                data.forEach(function(pub) {
                    Publication.pubSpecies({id: pub.id}, function (result) {
                        pub.pubSpecies = result;
                    });
                    Publication.ethnologies({id: pub.id}, function (result) {
                        pub.ethnologies = result;
                    });
                    Publication.inVitroPharmacos({id: pub.id}, function (result) {
                        pub.inVitroPharmacos = result;
                    });
                    Publication.inVivoPharmacos({id: pub.id}, function (result) {
                        pub.inVivoPharmacos = result;
                    });
                });
            });
    });
