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
                url: '/pub/{pubId}/pi/{piIds}',
                data: {
                    authorities: [],
                    pageTitle: 'Ingredient Details'
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
                        return PubSpecies.getByPubIdAndPiIds({pubId : $stateParams.pubId, piIds : $stateParams.piIds}).$promise;
                    }],
                    ethnology: ['$stateParams', 'Ethnology', function($stateParams, Ethnology){
                        return Ethnology.getByPubIdAndPiIds({pubId : $stateParams.pubId, piIds : $stateParams.piIds}).$promise;
                    }],
                    inVivoPharmacos: ['$stateParams', 'InVivoPharmaco', function($stateParams, InVivoPharmaco){
                        return InVivoPharmaco.getByPubIdAndPiIds({pubId : $stateParams.pubId, piIds : $stateParams.piIds}).$promise;
                    }],
                    inVitroPharmacos: ['$stateParams', 'InVitroPharmaco', function($stateParams, InVitroPharmaco){
                        return InVitroPharmaco.getByPubIdAndPiIds({pubId : $stateParams.pubId, piIds : $stateParams.piIds}).$promise;
                    }],
                    pubSummary: ['$stateParams', 'Publication', function($stateParams, Publication) {
                        return Publication.pubSummaryById({id : $stateParams.pubId}).$promise;
                    }],
                }
            })
    });
