'use strict';

angular.module('malariaplantdbApp')
    .controller('PublicationDetailController', function ($scope, $rootScope, $stateParams, entity, Publication, Author, Compiler, PubSpecies, Ethnology, InVivoPharmaco, InVitroPharmaco) {
        $scope.publication = entity;
        $scope.load = function (id) {
            Publication.get({id: id}, function(result) {
                $scope.publication = result;
            });
        };
        $rootScope.$on('malariaplantdbApp:publicationUpdate', function(event, result) {
            $scope.publication = result;
        });
    });
