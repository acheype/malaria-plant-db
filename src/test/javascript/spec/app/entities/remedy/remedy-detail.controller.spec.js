'use strict';

describe('Remedy Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockRemedy, MockPlantIngredient;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockRemedy = jasmine.createSpy('MockRemedy');
        MockPlantIngredient = jasmine.createSpy('MockPlantIngredient');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Remedy': MockRemedy,
            'PlantIngredient': MockPlantIngredient
        };
        createController = function() {
            $injector.get('$controller')("RemedyDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'malariaplantdbApp:remedyUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
