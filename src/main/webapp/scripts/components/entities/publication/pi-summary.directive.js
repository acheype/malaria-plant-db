'use strict';

angular.module('malariaplantdbApp')
    .directive('summary', function() {
        return {
            restrict: 'E',
            templateUrl: 'scripts/components/entities/publication/pi-summary.html',
            replace: true,
            scope: {
                publication: '=',
                plantIngredientStr: '=',
                piSummary: '='
            }
        };
    });
