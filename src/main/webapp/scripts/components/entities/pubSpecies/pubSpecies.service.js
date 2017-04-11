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
            'getByPubIdAndPiIds': {method: 'GET', url: 'api/publications/:pubId/pi/:piIds/pubSpecies', isArray: true},
            'update': { method:'PUT' }
        });
    });
