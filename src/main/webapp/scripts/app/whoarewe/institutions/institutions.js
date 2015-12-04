'use strict';

angular.module('malariaplantdbApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('institutions', {
                parent: 'whoarewe',
                url: '/institutions',
                data: {
                    authorities: [],
                    pageTitle: 'Institutions'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/whoarewe/institutions/institutions.html',
                        controller: 'InstitutionsController'
                    }
                },
                resolve: {}
            });
    });
