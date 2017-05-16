'use strict';

angular.module('malariaplantdbApp').controller('CompilerDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Compiler',
        function($scope, $stateParams, $uibModalInstance, entity, Compiler) {

        $scope.compiler = entity;
        $scope.load = function(id) {
            Compiler.get({id : id}, function(result) {
                $scope.compiler = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('malariaplantdbApp:compilerUpdate', result);
            $uibModalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.compiler.id != null) {
                Compiler.update($scope.compiler, onSaveFinished);
            } else {
                Compiler.save($scope.compiler, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
