'use strict';

angular.module('malariaplantdbApp')
    .factory('RemedySearch', function ($resource) {
        return $resource('api/_search/remedys/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
