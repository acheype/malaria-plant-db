'use strict';

angular.module('malariaplantdbApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('community', {
                parent: 'whoarewe',
                url: '/community',
                data: {
                    authorities: [],
                    pageTitle: 'Community'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/whoarewe/community/community.html',
                        controller: 'CommunityController'
                    }
                },
                resolve: {}
            });
    });
