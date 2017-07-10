'use strict';

angular.module('malariaplantdbApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('inVitroPharmaco', {
                parent: 'admin',
                url: '/inVitroPharmacos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'InVitroPharmacos'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/inVitroPharmaco/inVitroPharmacos.html',
                        controller: 'InVitroPharmacoController'
                    }
                },
                resolve: {
                }
            })
            .state('inVitroPharmaco.detail', {
                parent: 'admin',
                url: '/inVitroPharmaco/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'InVitroPharmaco'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/inVitroPharmaco/inVitroPharmaco-detail.html',
                        controller: 'InVitroPharmacoDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'InVitroPharmaco', function($stateParams, InVitroPharmaco) {
                        return InVitroPharmaco.get({id : $stateParams.id});
                    }]
                }
            })
            .state('inVitroPharmaco.new', {
                parent: 'inVitroPharmaco',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/inVitroPharmaco/inVitroPharmaco-dialog.html',
                        controller: 'InVitroPharmacoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {testedEntity: null, extractionSolvent: null, additiveProduct: null,
                                    compoundName: null, screeningTest: null, measureMethod: null, concentration: null,
                                    molConcentration: null, inhibition: null, ic50: null, molIc50: null,
                                    selectivityIndex: null, compilersObservations: null, id: null, remedy: {id: null,
                                        plantIngredients: []}};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('inVitroPharmaco', null, { reload: true });
                    }, function() {
                        $state.go('inVitroPharmaco');
                    })
                }]
            })
            .state('inVitroPharmaco.edit', {
                parent: 'inVitroPharmaco',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/inVitroPharmaco/inVitroPharmaco-dialog.html',
                        controller: 'InVitroPharmacoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InVitroPharmaco', function(InVitroPharmaco) {
                                return InVitroPharmaco.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('inVitroPharmaco', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
