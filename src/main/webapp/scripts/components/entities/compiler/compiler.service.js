'use strict';

angular.module('malariaplantdbApp')
    .factory('Compiler', function ($resource, DateUtils) {
        return $resource('api/compilers/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'sortedQuery': {method: 'GET', params: {sort: ['family,asc', 'given,asc', 'id,asc']}, isArray: true},
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
