'use strict';

angular.module('malariaplantdbApp')
    .factory('Species', function ($resource) {
        return $resource('api/species/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'sortedQuery': {method: 'GET', params: {sort: ['species,asc']}, isArray: true},
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
