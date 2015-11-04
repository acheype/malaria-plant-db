'use strict';

angular.module('malariaplantdbApp')
    .factory('PlantIngredientSearch', function ($resource) {
        return $resource('api/_search/plantIngredients/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
