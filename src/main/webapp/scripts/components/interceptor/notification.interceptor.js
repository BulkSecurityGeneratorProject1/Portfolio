 'use strict';

angular.module('portfolioApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-portfolioApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-portfolioApp-params')});
                }
                return response;
            },
        };
    });