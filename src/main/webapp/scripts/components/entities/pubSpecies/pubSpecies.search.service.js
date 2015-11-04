'use strict';

angular.module('malariaplantdbApp')
    .factory('PubSpeciesSearch', function ($resource) {
        return $resource('api/_search/pubSpecies/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
