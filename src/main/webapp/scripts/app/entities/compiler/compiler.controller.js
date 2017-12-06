'use strict';

angular.module('malariaplantdbApp')
    .controller('CompilerController', function ($scope, $window, Compiler, CompilerSearch, ParseLinks) {
        $scope.compilers = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Compiler.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.compilers = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Compiler.get({id: id}, function(result) {
                $scope.compiler = result;
                $('#deleteCompilerConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Compiler.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteCompilerConfirmation').modal('hide');
                    $scope.clear();
                    $window.scrollTo(0, 0);
                });
        };

        $scope.search = function () {
            CompilerSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.compilers = result;
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
            $scope.compiler = {family: null, given: null, institution: null, institutionAddress: null, email: null, id: null};
        };
    });
