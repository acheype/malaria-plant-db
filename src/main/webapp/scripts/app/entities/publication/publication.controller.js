'use strict';

angular.module('malariaplantdbApp')
    .controller('PublicationController', function ($scope, $window, Publication, PublicationSearch, ParseLinks) {
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

        $scope.delete = function (id) {
            Publication.get({id: id}, function(result) {
                $scope.publication = result;
                $('#deletePublicationConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Publication.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePublicationConfirmation').modal('hide');
                    $scope.clear();
                    $window.scrollTo(0, 0);
                });
        };

        $scope.search = function () {
            PublicationSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.publications = result;
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
            $scope.publication = {entryType: null, year: null, title: null, journal: null, pages: null, volume: null, nbOfVolumes: null, number: null, bookTitle: null, publisher: null, edition: null, conferenceName: null, conferencePlace: null, university: null, institution: null, doi: null, pmid: null, isbn: null, url: null, compilersNotes: null, id: null};
        };
    });
