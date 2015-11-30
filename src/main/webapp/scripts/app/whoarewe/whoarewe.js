'use strict';

angular.module('malariaplantdbApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('whoarewe', {
                abstract: true,
                parent: 'site'
            });
    });
