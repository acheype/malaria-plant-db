'use strict';

angular.module('malariaplantdbApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('pubSpecies', {
                parent: 'admin',
                url: '/pubSpecies',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'PubSpecies'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/pubSpecies/pubSpecies.html',
                        controller: 'PubSpeciesController'
                    }
                },
                resolve: {
                }
            })
            .state('pubSpecies.detail', {
                parent: 'admin',
                url: '/pubSpecies/{id:int}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'PubSpecies'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/pubSpecies/pubSpecies-detail.html',
                        controller: 'PubSpeciesDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'PubSpecies', function($stateParams, PubSpecies) {
                        return PubSpecies.get({id : $stateParams.id});
                    }]
                }
            })
            .state('pubSpecies.new', {
                parent: 'pubSpecies',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/pubSpecies/pubSpecies-dialog.html',
                        controller: 'PubSpeciesDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {speciesNameInPub: null, isHerbariumVoucher: false, herbarium : null, country: null, continent: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('pubSpecies', null, { reload: true });
                    }, function() {
                        $state.go('pubSpecies');
                    })
                }]
            })
            .state('pubSpecies.edit', {
                parent: 'pubSpecies',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/pubSpecies/pubSpecies-dialog.html',
                        controller: 'PubSpeciesDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['PubSpecies', function(PubSpecies) {
                                return PubSpecies.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('pubSpecies', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
