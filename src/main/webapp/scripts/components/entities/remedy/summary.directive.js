'use strict';

angular.module('malariaplantdbApp')
    .directive('summary', function() {
        return {
            restrict: 'E',
            templateUrl: 'scripts/components/entities/remedy/summary.html',
            replace: true,
            scope: {
                publication: '=',
                remStr: '=',
                remSummary: '='
            }
        };
    });
