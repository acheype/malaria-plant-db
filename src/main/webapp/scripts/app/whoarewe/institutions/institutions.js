'use strict';

angular.module('malariaplantdbApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('community', {
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
