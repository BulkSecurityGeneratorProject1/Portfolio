'use strict';

angular.module('portfolioApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


