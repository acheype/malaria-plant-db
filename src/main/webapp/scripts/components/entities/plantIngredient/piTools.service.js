'use strict';

angular.module('malariaplantdbApp')
    .factory('PiTools', function() {
        return {
            'partUsedList' : ['Aerial part', 'Aerial part with flower', 'Aerial part with fruit', 'Bark', 'Bulb',
                'Essential oil', 'Exsudate', 'Flower', 'Flower bud', 'Fruit', 'Heart wood', 'Inner bark', 'Latex',
                'Leaf', 'Leaf bud', 'Leaf petiole', 'Other', 'Part not defined', 'Petal', 'Rhizom', 'Root', 'Root bark',
                'Sap', 'Seed', 'Stem', 'Stem and leaf', 'Stem bark', 'Tendril', 'Whole plant'],

            'containsPlantIngredient' : function(species, partUsed, plantIngredientList) {
                return plantIngredientList.filter(function (listItem) {
                        return angular.equals(listItem.species, species) &&
                            angular.equals(listItem.partUsed, partUsed)
                    }).length > 0;}
        };
    });
