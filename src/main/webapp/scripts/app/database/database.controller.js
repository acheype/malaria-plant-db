'use strict';

angular.module('malariaplantdbApp')
    .controller('DatabaseController', function ($scope, Publication, PublicationSearch, ParseLinks) {
        $scope.pubSummaries = [];
        $scope.page = 0;

        $scope.loadAll = function() {
            Publication.pubSummaries({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.pubSummaries = result;
            });
        };

        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();
    });
