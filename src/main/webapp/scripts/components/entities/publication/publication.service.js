'use strict';

angular.module('malariaplantdbApp')
    .factory('Publication', function ($resource, DateUtils) {
        return $resource('api/publications/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'sortedQuery': {method: 'GET', params: {sort: ['title,asc']}, isArray: true},
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
