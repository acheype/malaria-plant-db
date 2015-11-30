'use strict';

angular.module('malariaplantdbApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('compiler', {
                parent: 'admin',
                url: '/compilers',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Compilers'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/compiler/compilers.html',
                        controller: 'CompilerController'
                    }
                },
                resolve: {
                }
            })
            .state('compiler.detail', {
                parent: 'admin',
                url: '/compiler/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Compiler'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/compiler/compiler-detail.html',
                        controller: 'CompilerDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Compiler', function($stateParams, Compiler) {
                        return Compiler.get({id : $stateParams.id});
                    }]
                }
            })
            .state('compiler.new', {
                parent: 'compiler',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/compiler/compiler-dialog.html',
                        controller: 'CompilerDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {family: null, given: null, institution: null, institutionAddress: null, email: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('compiler', null, { reload: true });
                    }, function() {
                        $state.go('compiler');
                    })
                }]
            })
            .state('compiler.edit', {
                parent: 'compiler',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/compiler/compiler-dialog.html',
                        controller: 'CompilerDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Compiler', function(Compiler) {
                                return Compiler.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('compiler', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
