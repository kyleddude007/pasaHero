(function() {
  var pasahero;

  pasahero = angular.module('Pasahero', ['Pasahero.services', 'Pasahero.controllers']);

  pasahero.config([
    '$compileProvider', function($compileProvider) {
      return $compileProvider.urlSanitizationWhitelist(/^\s*(https?|ftp|mailto|file|tel):/);
    }
  ]);

  pasahero.config([
    '$routeProvider', function($routeProvider) {
      return $routeProvider.when('/view', {
        controller: 'ViewCtrl',
        templateUrl: 'partials/view.html'
      }).when('/', {
        controller: 'MapCtrl',
        templateUrl: 'partials/map.html'
      }).otherwise({
        redirectTo: '/'
      });
    }
  ]);

}).call(this);
