'use strict';

angular.module('malariaplantdbApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('import-result', {
                parent: 'admin',
                url: '/import-result',
                params: {importStatus: null, filename: null},
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Data importation results'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/import/import-result.html',
                        controller: 'ImportResultController'
                    }
                },
                resolve: {
                    importStatus: ['$stateParams', function($stateParams){
                        return $stateParams.importStatus;
                    }],
                    filename: ['$stateParams', function($stateParams) {
                        return $stateParams.filename;
                    }]
                }
            });
    });
