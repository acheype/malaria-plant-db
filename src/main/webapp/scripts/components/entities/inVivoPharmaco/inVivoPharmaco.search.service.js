'use strict';

angular.module('malariaplantdbApp')
    .factory('InVivoPharmacoSearch', function ($resource) {
        return $resource('api/_search/inVivoPharmacos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
