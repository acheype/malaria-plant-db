'use strict';

angular.module('malariaplantdbApp')
    .factory('Species', function ($resource, DateUtils) {
        return $resource('api/species/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'sortedQuery': {method: 'GET', params: {sort: ['family,asc', 'species,asc']}, isArray: true},
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
