'use strict';

angular.module('malariaplantdbApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('dataentry', {
                abstract: true,
                parent: 'site'
            });
    });
