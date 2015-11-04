'use strict';

angular.module('malariaplantdbApp')
    .factory('EthnologySearch', function ($resource) {
        return $resource('api/_search/ethnologies/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
