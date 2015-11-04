'use strict';

angular.module('malariaplantdbApp')
    .factory('InVitroPharmacoSearch', function ($resource) {
        return $resource('api/_search/inVitroPharmacos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
