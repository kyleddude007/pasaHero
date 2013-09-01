initializePhoneGap = (success, failure) ->
  timer = window.setInterval(->
    if window.device
      window.clearInterval timer
      success()
  , 100)
  window.setTimeout (-> #failsafe
    unless window.device #phonegap failed
      window.clearInterval timer
      failure()
  ), 5000 #5 seconds

window.onload = ->
  initializePhoneGap (success = ->
    
    #start app
    console.log "phonegap: success"
    angular.bootstrap document, ['Pasahero']
    ,
    false  
  ), failure = ->
    
    #phonegap failed 
    console.log "phonegap: failure"


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