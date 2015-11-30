'use strict';

angular.module('malariaplantdbApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('register', {
                parent: 'admin',
                url: '/register',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Register a new admin'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/account/register/register.html',
                        controller: 'RegisterController'
                    }
                },
                resolve: {}
            });
    });
