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
            'getByPubIdAndRemedyId': {method: 'GET', url: 'api/publications/:pubId/remedy/:remId/pubSpecies', isArray: true},
            'update': { method:'PUT' }
        });
    });
