'use strict';

angular.module('malariaplantdbApp').controller('PublicationDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Publication', 'Author', 'Compiler',
        function($scope, $stateParams, $uibModalInstance, entity, Publication, Author, Compiler) {

        $scope.publication = entity;
        $scope.authors = Author.query();
        $scope.compilers = Compiler.sortedQuery();

        $scope.load = function(id) {
            Publication.get({id : id}, function(result) {
                $scope.publication = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('malariaplantdbApp:publicationUpdate', result);
            $uibModalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.publication.id != null) {
                Publication.update($scope.publication, onSaveFinished);
            } else {
                Publication.save($scope.publication, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
