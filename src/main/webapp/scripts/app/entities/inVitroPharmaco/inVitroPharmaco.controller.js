'use strict';

angular.module('malariaplantdbApp')
    .controller('InVitroPharmacoController', function ($scope, InVitroPharmaco, InVitroPharmacoSearch, ParseLinks) {
        $scope.inVitroPharmacos = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InVitroPharmaco.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.inVitroPharmacos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            InVitroPharmaco.get({id: id}, function(result) {
                $scope.inVitroPharmaco = result;
                $('#deleteInVitroPharmacoConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            InVitroPharmaco.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteInVitroPharmacoConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            InVitroPharmacoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.inVitroPharmacos = result;
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
            $scope.inVitroPharmaco = {testedEntity: null, extractionSolvent: null, additiveProduct: null,
                compoundName: null, screeningTest: null, measureMethod: null, concentration: null,
                molConcentration: null, inhibition: null, ic50: null, molIc50: null, selectivityIndex: null,
                compilersObservations: null, id: null, remedy: {id: null, plantIngredients: []}};
        };
    });
