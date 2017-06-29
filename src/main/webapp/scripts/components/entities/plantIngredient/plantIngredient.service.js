'use strict';

angular.module('malariaplantdbApp')
    .factory('PlantIngredient', function ($resource, DateUtils) {
        return $resource('api/plantIngredients/:id', {}, {
            'query': { method: 'GET', isArray: true},
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
