pasahero = angular.module 'Pasahero', [
  'Pasahero.services'
  'Pasahero.controllers'
]

pasahero.config ['$compileProvider', ($compileProvider) ->
  $compileProvider.urlSanitizationWhitelist /^\s*(https?|ftp|mailto|file|tel):/
]

pasahero.config ['$routeProvider', ($routeProvider) ->
  $routeProvider
    #.when '/',
    #  controller: 'MainCtrl'
    #  templateUrl: 'partials/main.html'
    .when '/view',
      controller: 'ViewCtrl'
      templateUrl: 'partials/view.html'
    .when '/',
      controller: 'MapCtrl'
      templateUrl: 'partials/map.html'
    .otherwise redirectTo: '/'
]