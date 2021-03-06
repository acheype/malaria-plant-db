'use strict';

angular.module('malariaplantdbApp')
    .factory('InVivoPharmaco', function ($resource, DateUtils) {
        return $resource('api/inVivoPharmacos/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'getByPubIdAndRemedyId': {method: 'GET', url: 'api/publications/:pubId/remedy/:remId/inVivoPharmacos',
                isArray: true},
            'update': { method:'PUT' }
        });
    });
