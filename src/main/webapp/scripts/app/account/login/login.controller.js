'use strict';

angular.module('malariaplantdbApp')
    .controller('LoginController', function ($rootScope, $scope, $state, $timeout, Auth) {
        $scope.user = {};
        $scope.errors = {};

        $scope.rememberMe = true;
        $timeout(function (){angular.element('[ng-model="username"]').focus();});
        $scope.login = function (event) {
            event.preventDefault();
            Auth.login({
                username: $scope.username,
                password: $scope.password,
                rememberMe: $scope.rememberMe
            }).then(function () {
                $scope.authenticationError = false;
                // to manage the bug that the navbar was not updated after the login, a reload was added and there
                // is now only a single case which return to the home
                //if ($rootScope.previousStateName === 'register') {
                    $state.go('home', {}, {reload: true});
                //} else {
                //    $rootScope.back();
                //}
            }).catch(function () {
                $scope.authenticationError = true;
            });
        };
    });
