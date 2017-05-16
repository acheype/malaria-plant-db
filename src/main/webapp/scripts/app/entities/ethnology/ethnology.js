'use strict';

angular.module('malariaplantdbApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('ethnology', {
                parent: 'admin',
                url: '/ethnologies',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Ethnologies'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/ethnology/ethnologies.html',
                        controller: 'EthnologyController'
                    }
                },
                resolve: {
                }
            })
            .state('ethnology.detail', {
                parent: 'admin',
                url: '/ethnology/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Ethnology'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/ethnology/ethnology-detail.html',
                        controller: 'EthnologyDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Ethnology', function($stateParams, Ethnology) {
                        return Ethnology.get({id : $stateParams.id});
                    }]
                }
            })
            .state('ethnology.new', {
                parent: 'ethnology',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/ethnology/ethnology-dialog.html',
                        controller: 'EthnologyDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {ethnoRelevancy: null, treatmentType: null, traditionalRecipeDetails: null, preparationMode: null, administrationRoute: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('ethnology', null, { reload: true });
                    }, function() {
                        $state.go('ethnology');
                    })
                }]
            })
            .state('ethnology.edit', {
                parent: 'ethnology',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/ethnology/ethnology-dialog.html',
                        controller: 'EthnologyDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Ethnology', function(Ethnology) {
                                return Ethnology.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('ethnology', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
