'use strict';

angular.module('malariaplantdbApp')
    .factory('Ethnology', function ($resource, DateUtils) {
        return $resource('api/ethnologies/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'getByPubIdAndPiIds': {method: 'GET', url: 'api/publications/:pubId/pi/:piIds/ethnology'},
            'update': { method:'PUT' }
        });
    });
