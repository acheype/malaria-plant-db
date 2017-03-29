'use strict';

angular.module('malariaplantdbApp')
    .controller('MainController', function ($scope, Principal) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });

        $scope.carouselInterval = 5000;
        $scope.slides = [{image: "assets/images/mpdbc_carousel1.jpg", text: ""},
            {image: "assets/images/mpdbc_carousel2.jpg", text: ""},
            {image: "assets/images/mpdbc_carousel3.jpg", text: ""},
            {image: "assets/images/mpdbc_carousel4.jpg", text: ""}
        ];

        $scope.cloudWords = [
            {text: "biodiversity", weight: 7},
            {text: "malaria", weight: 9},
            {text: "remedy", weight: 5},
            {text: "biological evaluation", weight: 4},
            {text: "Plasmodium", weight: 6},
            {text: "traditional medecine", weight: 8},
            {text: "toxicity", weight: 5},
            {text: "medicinal plant", weight: 10}
        ];
        $scope.wordColors = ["#8cc542", "#3b98a9"];

    });
