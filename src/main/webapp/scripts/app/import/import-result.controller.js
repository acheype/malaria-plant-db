'use strict';

angular.module('malariaplantdbApp')
    .controller('ImportResultController', function ($scope, importStatus, filename) {
        $scope.importStatus = importStatus;
        $scope.filename = filename;
    });
