'use strict';

angular.module('malariaplantdbApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('inVivoPharmaco', {
                parent: 'admin',
                url: '/inVivoPharmacos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'InVivoPharmacos'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/inVivoPharmaco/inVivoPharmacos.html',
                        controller: 'InVivoPharmacoController'
                    }
                },
                resolve: {
                }
            })
            .state('inVivoPharmaco.detail', {
                parent: 'admin',
                url: '/inVivoPharmaco/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'InVivoPharmaco'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/inVivoPharmaco/inVivoPharmaco-detail.html',
                        controller: 'InVivoPharmacoDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'InVivoPharmaco', function($stateParams, InVivoPharmaco) {
                        return InVivoPharmaco.get({id : $stateParams.id});
                    }]
                }
            })
            .state('inVivoPharmaco.new', {
                parent: 'inVivoPharmaco',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/inVivoPharmaco/inVivoPharmaco-dialog.html',
                        controller: 'InVivoPharmacoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {testedEntity: null, extractionSolvent: null, additiveProduct: null,
                                    compoundName: null, screeningTest: null, treatmentRoute: null, dose: null,
                                    inhibition: null, survivalPercent: null, survivalTime: null, ed50: null,
                                    ld50: null, compilersObservations: null, id: null, remedy: {id: null,
                                        plantIngredients: []}};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('inVivoPharmaco', null, { reload: true });
                    }, function() {
                        $state.go('inVivoPharmaco');
                    })
                }]
            })
            .state('inVivoPharmaco.edit', {
                parent: 'inVivoPharmaco',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/inVivoPharmaco/inVivoPharmaco-dialog.html',
                        controller: 'InVivoPharmacoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InVivoPharmaco', function(InVivoPharmaco) {
                                return InVivoPharmaco.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('inVivoPharmaco', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
