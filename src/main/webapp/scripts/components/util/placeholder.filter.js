'use strict';

const PLACEHOLDER_STR = '-';

angular.module('malariaplantdbApp')
    .filter('placeholder', function () {
        return function (text) {
            // show placeholder in case of null or undefined
            if (!text) return PLACEHOLDER_STR;
            // If we're dealing with a function, get the value
            if (angular.isFunction(text)) text = text();
            if (typeof text != 'string') return text;
            // Trim any whitespace and show placeholder if no content
            else return text.trim() || PLACEHOLDER_STR;
        }
    });
