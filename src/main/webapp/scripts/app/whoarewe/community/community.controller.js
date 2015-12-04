'use strict';

angular.module('malariaplantdbApp')
    .controller('CommunityController', function ($scope, Compiler) {
        $scope.compilers = Compiler.sortedQuery();
    });
