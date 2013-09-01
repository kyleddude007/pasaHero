(function() {
  var initializePhoneGap, pasahero;

  initializePhoneGap = function(success, failure) {
    var timer;
    timer = window.setInterval(function() {
      if (window.device) {
        window.clearInterval(timer);
        return success();
      }
    }, 100);
    return window.setTimeout((function() {
      if (!window.device) {
        window.clearInterval(timer);
        return failure();
      }
    }), 5000);
  };

  window.onload = function() {
    var failure, success;
    return initializePhoneGap((success = function() {
      console.log("phonegap: success");
      return angular.bootstrap(document, ['Pasahero'], false);
    }), failure = function() {
      return console.log("phonegap: failure");
    });
  };

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
