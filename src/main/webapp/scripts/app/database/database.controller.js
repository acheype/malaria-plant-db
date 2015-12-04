'use strict';

angular.module('malariaplantdbApp')
    .controller('DatabaseController', function ($scope, Publication, PublicationSearch, ParseLinks) {
        $scope.publications = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Publication.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.publications = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();
    });
