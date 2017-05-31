'use strict';

angular.module('malariaplantdbApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('remedy', {
                parent: 'entity',
                url: '/remedys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Remedys'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/remedy/remedies.html',
                        controller: 'RemedyController'
                    }
                },
                resolve: {
                }
            })
            .state('remedy.detail', {
                parent: 'entity',
                url: '/remedy/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Remedy'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/remedy/remedy-detail.html',
                        controller: 'RemedyDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Remedy', function($stateParams, Remedy) {
                        return Remedy.get({id : $stateParams.id});
                    }]
                }
            })
            .state('remedy.new', {
                parent: 'remedy',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/remedy/remedy-dialog.html',
                        controller: 'RemedyDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('remedy', null, { reload: true });
                    }, function() {
                        $state.go('remedy');
                    })
                }]
            })
            .state('remedy.edit', {
                parent: 'remedy',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/remedy/remedy-dialog.html',
                        controller: 'RemedyDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Remedy', function(Remedy) {
                                return Remedy.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('remedy', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
