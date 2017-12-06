'use strict';

angular.module('malariaplantdbApp')
    .controller('SpeciesController', function ($scope, $window, Species, SpeciesSearch, ParseLinks) {
        $scope.speciesList = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Species.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.speciesList = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Species.get({id: id}, function(result) {
                $scope.species = result;
                $('#deleteSpeciesConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Species.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteSpeciesConfirmation').modal('hide');
                    $scope.clear();
                    $window.scrollTo(0, 0);
                });
        };

        $scope.search = function () {
            SpeciesSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.speciesList = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.species = {family: null, species: null, id: null};
        };
    });
