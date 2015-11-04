'use strict';

angular.module('malariaplantdbApp')
    .controller('EthnologyController', function ($scope, Ethnology, EthnologySearch, ParseLinks) {
        $scope.ethnologies = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Ethnology.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.ethnologies = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Ethnology.get({id: id}, function(result) {
                $scope.ethnology = result;
                $('#deleteEthnologyConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Ethnology.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteEthnologyConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            EthnologySearch.query({query: $scope.searchQuery}, function(result) {
                $scope.ethnologies = result;
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
            $scope.ethnology = {ethnoRelevancy: null, treatmentType: null, traditionalRecipeDetails: null, preparationMode: null, administrationRoute: null, id: null};
        };
    });
