'use strict';

angular.module('malariaplantdbApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
