'use strict';

angular.module('malariaplantdbApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('publication', {
                parent: 'admin',
                url: '/publications',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Publications'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/publication/publications.html',
                        controller: 'PublicationController'
                    }
                },
                resolve: {
                }
            })
            .state('publication.detail', {
                parent: 'admin',
                url: '/publication/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Publication'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/publication/publication-detail.html',
                        controller: 'PublicationDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Publication', function($stateParams, Publication) {
                        return Publication.get({id : $stateParams.id});
                    }]
                }
            })
            .state('publication.new', {
                parent: 'publication',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/publication/publication-dialog.html',
                        controller: 'PublicationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {entryType: null, year: null, title: null, journal: null, pages: null, volume: null, nbOfVolumes: null, number: null, bookTitle: null, publisher: null, edition: null, conferenceName: null, conferencePlace: null, university: null, institution: null, doi: null, pmid: null, isbn: null, url: null, compilersNotes: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('publication', null, { reload: true });
                    }, function() {
                        $state.go('publication');
                    })
                }]
            })
            .state('publication.edit', {
                parent: 'publication',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/publication/publication-dialog.html',
                        controller: 'PublicationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Publication', function(Publication) {
                                return Publication.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('publication', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
