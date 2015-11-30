'use strict';

angular.module('malariaplantdbApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('background', {
                parent: 'whoarewe',
                url: '/background',
                data: {
                    authorities: [],
                    pageTitle: 'Background'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/whoarewe/background/background.html',
                        controller: 'BackgroundController'
                    }
                },
                resolve: {}
            });
    });
