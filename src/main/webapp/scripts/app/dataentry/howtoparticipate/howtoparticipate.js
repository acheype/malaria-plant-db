'use strict';

angular.module('malariaplantdbApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('howtoparticipate', {
                parent: 'dataentry',
                url: '/howtoparticipate',
                data: {
                    authorities: [],
                    pageTitle: 'How to participate ?'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/dataentry/howtoparticipate/howtoparticipate.html',
                        controller: 'HowToParticipateController'
                    }
                },
                resolve: {}
            });
    });
