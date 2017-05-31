'use strict';

angular.module('malariaplantdbApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('database', {
                parent: 'site',
                url: '/database',
                data: {
                    authorities: [],
                    pageTitle: 'Database'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/database/database.html',
                        controller: 'DatabaseController'
                    }
                },
                resolve: {}
            })
            .state('database.detail',{
                parent: 'database',
                url: '/pub/{pubId}/remedy/{remId}',
                data: {
                    authorities: [],
                    pageTitle: 'Remedy Details'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/database/database-detail.html',
                        controller: 'DatabaseDetailController'
                    }
                },
                resolve: {
                    publication: ['$stateParams', 'Publication', function($stateParams, Publication) {
                        return Publication.get({id : $stateParams.pubId}).$promise;
                    }],
                    pubSpecies: ['$stateParams', 'PubSpecies', function($stateParams, PubSpecies){
                        return PubSpecies.getByPubIdAndRemedyId({pubId : $stateParams.pubId, remId : $stateParams.remId}).$promise;
                    }],
                    ethnology: ['$stateParams', 'Ethnology', function($stateParams, Ethnology){
                        return Ethnology.getByPubIdAndRemedyId({pubId : $stateParams.pubId, remId : $stateParams.remId}).$promise;
                    }],
                    inVivoPharmacos: ['$stateParams', 'InVivoPharmaco', function($stateParams, InVivoPharmaco){
                        return InVivoPharmaco.getByPubIdAndRemedyId({pubId : $stateParams.pubId, remId : $stateParams.remId}).$promise;
                    }],
                    inVitroPharmacos: ['$stateParams', 'InVitroPharmaco', function($stateParams, InVitroPharmaco){
                        return InVitroPharmaco.getByPubIdAndRemedyId({pubId : $stateParams.pubId, remId : $stateParams.remId}).$promise;
                    }],
                    pubSummary: ['$stateParams', 'Publication', function($stateParams, Publication) {
                        return Publication.pubSummaryById({id : $stateParams.pubId}).$promise;
                    }],
                }
            })
    });
