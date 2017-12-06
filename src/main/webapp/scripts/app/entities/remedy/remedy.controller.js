'use strict';

angular.module('malariaplantdbApp')
    .controller('RemedyController', function ($scope, $window, Remedy, RemedySearch, ParseLinks) {
        $scope.remedys = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Remedy.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.remedys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Remedy.get({id: id}, function(result) {
                $scope.remedy = result;
                $('#deleteRemedyConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Remedy.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteRemedyConfirmation').modal('hide');
                    $scope.clear();
                    $window.scrollTo(0, 0);
                });
        };

        $scope.search = function () {
            RemedySearch.query({query: $scope.searchQuery}, function(result) {
                $scope.remedys = result;
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
            $scope.remedy = {
                id: null
            };
        };
    });
