'use strict';

angular.module('malariaplantdbApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('plantIngredient', {
                parent: 'entity',
                url: '/plantIngredients',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'PlantIngredients'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/plantIngredient/plantIngredients.html',
                        controller: 'PlantIngredientController'
                    }
                },
                resolve: {
                }
            })
            .state('plantIngredient.detail', {
                parent: 'entity',
                url: '/plantIngredient/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'PlantIngredient'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/plantIngredient/plantIngredient-detail.html',
                        controller: 'PlantIngredientDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'PlantIngredient', function($stateParams, PlantIngredient) {
                        return PlantIngredient.get({id : $stateParams.id});
                    }]
                }
            })
            .state('plantIngredient.new', {
                parent: 'plantIngredient',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/plantIngredient/plantIngredient-dialog.html',
                        controller: 'PlantIngredientDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {partUsed: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('plantIngredient', null, { reload: true });
                    }, function() {
                        $state.go('plantIngredient');
                    })
                }]
            })
            .state('plantIngredient.edit', {
                parent: 'plantIngredient',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/plantIngredient/plantIngredient-dialog.html',
                        controller: 'PlantIngredientDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['PlantIngredient', function(PlantIngredient) {
                                return PlantIngredient.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('plantIngredient', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
