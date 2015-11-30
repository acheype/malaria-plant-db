'use strict';

angular.module('malariaplantdbApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('database', {
                parent: 'site',
                url: '/database',
                data: {
                    authorities: [],
                    pageTitle: 'Database'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/database/database.html',
                        controller: 'DatabaseController'
                    }
                },
                resolve: {}
            });
    });
