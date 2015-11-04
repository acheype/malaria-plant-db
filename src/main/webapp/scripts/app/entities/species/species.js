'use strict';

angular.module('malariaplantdbApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('species', {
                parent: 'entity',
                url: '/species',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Species'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/species/species.html',
                        controller: 'SpeciesController'
                    }
                },
                resolve: {
                }
            })
            .state('species.detail', {
                parent: 'entity',
                url: '/species/{id:int}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Species'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/species/species-detail.html',
                        controller: 'SpeciesDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Species', function($stateParams, Species) {
                        return Species.get({id : $stateParams.id});
                    }]
                }
            })
            .state('species.new', {
                parent: 'species',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/species/species-dialog.html',
                        controller: 'SpeciesDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {family: null, species: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('species', null, { reload: true });
                    }, function() {
                        $state.go('species');
                    })
                }]
            })
            .state('species.edit', {
                parent: 'species',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/species/species-dialog.html',
                        controller: 'SpeciesDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Species', function(Species) {
                                return Species.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('species', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
