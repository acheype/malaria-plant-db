'use strict';

angular.module('malariaplantdbApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('settings', {
                parent: 'admin',
                url: '/settings',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Your settings'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/account/settings/settings.html',
                        controller: 'SettingsController'
                    }
                },
                resolve: {}
            });
    });
