'use strict';

angular.module('malariaplantdbApp')
    .controller('PubSpeciesController', function ($scope, $window, PubSpecies, PubSpeciesSearch, ParseLinks) {
        $scope.pubSpeciesList = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            PubSpecies.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.pubSpeciesList = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            PubSpecies.get({id: id}, function(result) {
                $scope.pubSpecies = result;
                $('#deletePubSpeciesConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            PubSpecies.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePubSpeciesConfirmation').modal('hide');
                    $scope.clear();
                    $window.scrollTo(0, 0);
                });
        };

        $scope.search = function () {
            PubSpeciesSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.pubSpeciesList = result;
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
            $scope.pubSpecies = {speciesNameInPub: null, isHerbariumVoucher: false, herbarium: null, country: null, continent: null, id: null};
        };
    });
