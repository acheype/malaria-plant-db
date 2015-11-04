 'use strict';

angular.module('malariaplantdbApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-malariaplantdbApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-malariaplantdbApp-params')});
                }
                return response;
            }
        };
    });
