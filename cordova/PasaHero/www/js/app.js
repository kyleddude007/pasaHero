(function() {
  angular.module("App", ["App.services", "App.controllers"]).config([
    "$compileProvider", function($compileProvider) {
      return $compileProvider.urlSanitizationWhitelist(/^\s*(https?|ftp|mailto|file|tel):/);
    }
  ]).config([
    "$routeProvider", function($routeProvider) {
      return $routeProvider.when("/", {
        controller: "MainCtrl",
        templateUrl: "partials/main.html"
      }).when("/view", {
        controller: "ViewCtrl",
        templateUrl: "partials/view.html"
      }).otherwise({
        redirectTo: "/"
      });
    }
  ]);

}).call(this);
