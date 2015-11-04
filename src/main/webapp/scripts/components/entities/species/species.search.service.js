'use strict';

angular.module('malariaplantdbApp')
    .factory('SpeciesSearch', function ($resource) {
        return $resource('api/_search/species/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
