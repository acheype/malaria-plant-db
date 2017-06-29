'use strict';

angular.module('malariaplantdbApp')
    .factory('Publication', function ($resource) {
        return $resource('api/publications/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'sortedQuery': {method: 'GET', params: {sort: ['title,asc']}, isArray: true},
            'pubSummaries' : {method: 'GET', url: 'api/pubSummaries', params: {sort: ['citation,asc']},
                isArray: true},
            'pubSummaryById': {method: 'GET', url : 'api/pubSummaries/:id',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'pubSpecies': {method: 'GET', url: 'api/publications/:id/pubspecies', isArray: true},
            'ethnologies': {method: 'GET', url: 'api/publications/:id/ethnologies', isArray: true},
            'inVivoPharmacos': {method: 'GET', url: 'api/publications/:id/inVivoPharmacos', isArray: true},
            'inVitroPharmacos': {method: 'GET', url: 'api/publications/:id/inVitroPharmacos', isArray: true},
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
