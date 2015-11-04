'use strict';

angular.module('malariaplantdbApp')
    .controller('InVivoPharmacoController', function ($scope, InVivoPharmaco, InVivoPharmacoSearch, ParseLinks) {
        $scope.inVivoPharmacos = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InVivoPharmaco.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.inVivoPharmacos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            InVivoPharmaco.get({id: id}, function(result) {
                $scope.inVivoPharmaco = result;
                $('#deleteInVivoPharmacoConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            InVivoPharmaco.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteInVivoPharmacoConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            InVivoPharmacoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.inVivoPharmacos = result;
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
            $scope.inVivoPharmaco = {testedEntity: null, extractionSolvent: null, additiveProduct: null, compoundName: null, screeningTest: null, treatmentRoute: null, dose: null, inhibition: null, survivalPercent: null, survivalTime: null, ed50: null, ld50: null, compilersObservations: null, id: null};
        };
    });
