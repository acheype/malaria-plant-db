'use strict';

angular.module('malariaplantdbApp').controller('AuthorDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Author', 'Publication',
        function ($scope, $stateParams, $uibModalInstance, entity, Author, Publication) {

        $scope.author = entity;
        $scope.publications = Publication.sortedQuery();
        $scope.load = function(id) {
            Author.get({id : id}, function(result) {
                $scope.author = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('malariaplantdbApp:authorUpdate', result);
            $uibModalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.author.id != null) {
                Author.update($scope.author, onSaveFinished);
            } else {
                Author.save($scope.author, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
