'use strict';

angular.module('malariaplantdbApp')
    .factory('CompilerSearch', function ($resource) {
        return $resource('api/_search/compilers/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
