'use strict';

angular.module('malariaplantdbApp')
    .factory('Remedy', function ($resource, DateUtils) {
        return $resource('api/remedys/:id', {}, {
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
