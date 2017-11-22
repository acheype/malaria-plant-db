'use strict';

angular.module('malariaplantdbApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('import', {
                parent: 'admin',
                url: '/import',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Data importation'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/import/import.html',
                        controller: 'ImportController'
                    }
                },
                resolve: {
                }
            });
    });
