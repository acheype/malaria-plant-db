'use strict';

angular.module('malariaplantdbApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('password', {
                parent: 'admin',
                url: '/password',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Your password'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/account/password/password.html',
                        controller: 'PasswordController'
                    }
                },
                resolve: {}
            });
    });
