angular.module("App", ["App.services", "App.controllers"]).config(["$compileProvider", ($compileProvider) ->
  $compileProvider.urlSanitizationWhitelist /^\s*(https?|ftp|mailto|file|tel):/
]).config ["$routeProvider", ($routeProvider) ->
  $routeProvider.when("/",
    controller: "MainCtrl"
    templateUrl: "partials/main.html"
  ).when("/view",
    controller: "ViewCtrl"
    templateUrl: "partials/view.html"
  ).otherwise redirectTo: "/"
]