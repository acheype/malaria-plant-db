'use strict';

angular.module('malariaplantdbApp')
    .factory('Publication', function ($resource, DateUtils) {
        return $resource('api/publications/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'sortedQuery': {method: 'GET', params: {sort: ['title,asc']}, isArray: true},
            'pubSummaries' : {method: 'GET', url: 'api/pubSummaries', params: {sort: ['citation,asc']},
                isArray: true},
            'pubSpecies': {method: 'GET', url: 'api/publications/:id/pubspecies', isArray: true},
            'ethnologies': {method: 'GET', url: 'api/publications/:id/ethnologies', isArray: true},
            'inVitroPharmacos': {method: 'GET', url: 'api/publications/:id/invitropharmacos', isArray: true},
            'inVivoPharmacos': {method: 'GET', url: 'api/publications/:id/invivopharmacos', isArray: true},
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
