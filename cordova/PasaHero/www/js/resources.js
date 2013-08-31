(function() {
  var resources;

  resources = angular.module('Pasahero.resources', ['ngResource']);

  resources.factory('Plan', [
    '$resource', '$http', function($resource, $http) {
      var Plan;
      return Plan = $resource('http://localhost:port/opentripplanner-api-webapp/ws/plan', {
        port: ':8080',
        arriveBy: '@arriveBy',
        time: '@time',
        mode: '@mode',
        optimize: '@optimize',
        maxWalkDistance: '@maxWalkDistance',
        walkSpeed: '@walkSpeed',
        date: '@date',
        toPlace: '@toPlace',
        fromPlace: '@fromPlace'
      }, {
        list: {
          method: 'GET',
          isArray: true
        },
        get: {
          method: 'JSONP',
          params: {
            callback: 'JSON_CALLBACK'
          }
        }
      });
    }
  ]);

}).call(this);
