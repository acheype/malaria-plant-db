'use strict';

angular.module('malariaplantdbApp')
    .controller('PlantIngredientController', function ($scope, PlantIngredient, PlantIngredientSearch, ParseLinks) {
        $scope.plantIngredients = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            PlantIngredient.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.plantIngredients = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            PlantIngredient.get({id: id}, function(result) {
                $scope.plantIngredient = result;
                $('#deletePlantIngredientConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            PlantIngredient.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePlantIngredientConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            PlantIngredientSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.plantIngredients = result;
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
            $scope.plantIngredient = {partUsed: null, id: null};
        };
    });
