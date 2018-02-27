'use strict';

angular.module('malariaplantdbApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('howtoenterdata', {
                parent: 'dataentry',
                url: '/howtoenterdata',
                data: {
                    authorities: [],
                    pageTitle: 'How to enter data ?'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/dataentry/howtoenterdata/howtoenterdata.html',
                        controller: 'HowToEnterDataController'
                    }
                },
                resolve: {}
            });
    });
