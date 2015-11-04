'use strict';

angular.module('malariaplantdbApp')
    .factory('PubSpecies', function ($resource, DateUtils) {
        return $resource('api/pubSpecies/:id', {}, {
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
