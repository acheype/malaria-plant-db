'use strict';

angular.module('malariaplantdbApp')
    .factory('InVitroPharmaco', function ($resource, DateUtils) {
        return $resource('api/inVitroPharmacos/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
