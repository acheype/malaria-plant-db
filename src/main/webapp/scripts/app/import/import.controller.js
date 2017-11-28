'use strict';

angular.module('malariaplantdbApp')
    .controller('ImportController', function ($scope, $state, Upload, $timeout) {
        $scope.loadingImage = false;

        $scope.process = function() {
            if ($scope.importForm.$valid && $scope.file) {
                $scope.uploadFile($scope.file);
                $scope.loadingImage = true;
            }
        }

        $scope.uploadFile = function(file) {
            Upload.upload({
                url: '/api/import',
                file: file
            }).then(function (response) {
                $timeout(function () {
                    $state.go('import-result', {importStatus: response.data, filename: $scope.file.name});
                });
            }, function (response) {
                if (response.status != 200)
                    $scope.errorResponse = response;
            });
        }
    });
