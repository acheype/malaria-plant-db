'use strict';

angular.module('malariaplantdbApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


