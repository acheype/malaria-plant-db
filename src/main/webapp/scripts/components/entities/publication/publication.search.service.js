'use strict';

angular.module('malariaplantdbApp')
    .factory('PublicationSearch', function ($resource) {
        return $resource('api/_search/publications/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
