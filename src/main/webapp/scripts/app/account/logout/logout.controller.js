'use strict';

angular.module('portfolioApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
