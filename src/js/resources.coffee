resources = angular.module 'Pasahero.resources', [
  'ngResource'
]

resources.factory 'Plan', ['$resource', '$http', ($resource, $http) ->

  Plan = $resource 'http://localhost:port/opentripplanner-api-webapp/ws/plan',
    {
      port: ':8080'
      arriveBy: '@arriveBy'
      time: '@time'
      mode: '@mode'
      optimize: '@optimize'
      maxWalkDistance: '@maxWalkDistance'
      walkSpeed: '@walkSpeed'
      date: '@date'
      toPlace: '@toPlace'
      fromPlace: '@fromPlace'
    },
    {
      list:
        method: 'GET'
        isArray: true
      ,
      get:
        method: 'JSONP'
        params: 
          callback: 'JSON_CALLBACK'
    }
    
]