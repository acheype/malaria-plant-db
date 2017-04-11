'use strict';

angular.module('malariaplantdbApp')
    .controller('DatabaseDetailController', function ($scope, $rootScope, $stateParams, publication, ethnologies,
                                                      pubSpecies, inVivoPharmacos) {

        //$window.document.title = titleKey;
        $scope.publication = publication;
        $scope.pubSpecies = pubSpecies;
        $scope.ethnologies = ethnologies;
        $scope.inVivoPharmacos = inVivoPharmacos;


            //.$promise.then(function(data){
            //    data.forEach(function(pub) {
            //        Publication.pubSpecies({id: pub.id}, function (result) {
            //            pub.pubSpecies = result;
            //        });
            //        Publication.ethnologies({id: pub.id}, function (result) {
            //            pub.ethnologies = result;
            //        });
            //        Publication.inVitroPharmacos({id: pub.id}, function (result) {
            //            pub.inVitroPharmacos = result;
            //        });
            //        Publication.inVivoPharmacos({id: pub.id}, function (result) {
            //            pub.inVivoPharmacos = result;
            //        });
            //    });
            //});
    });
