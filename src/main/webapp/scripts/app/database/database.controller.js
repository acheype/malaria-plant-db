'use strict';

angular.module('malariaplantdbApp')
    .controller('DatabaseController', function ($scope, $window, Publication, PublicationSearch, ParseLinks) {
        $scope.pubSummaries = [];
        $scope.page = 0;

        $scope.loadAll = function() {
            Publication.pubSummaries({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.resultNb = headers('X-Total-Count');
                $scope.pubSummaries = result;
            });
        };

        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.search = function () {
            PublicationSearch.nestedQuery({query: $scope.searchQuery}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.resultNb = headers('X-Total-Count');
                $scope.pubSummaries = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };
    })

;


