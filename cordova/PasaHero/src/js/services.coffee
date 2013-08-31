services = angular.module 'Pasahero.services', []

services.factory 'cordovaReady', [->
  (fn) ->
    queue = []
    impl = ->
      queue.push [].slice.call(arguments_)

    document.addEventListener 'deviceready', (->
      queue.forEach (args) ->
        fn.apply this, args

      impl = fn
    ), false
    ->
      impl.apply this, arguments_
]

services.factory 'mapSearchOptions', ->
  mapSearchOptions=
    'transit': false
    ,
    'bus': false    
    ,
    'train': false  
    ,
    'walk': false
    ,
    'bycicle': false

services.factory "geolocation", ['$rootScope', 'cordovaReady', ($rootScope, cordovaReady) ->
  getCurrentPosition: cordovaReady((onSuccess, onError, options) ->
    navigator.geolocation.getCurrentPosition (->
      that = this
      args = arguments_
      if onSuccess
        $rootScope.$apply ->
          onSuccess.apply that, args

    ), (->
      that = this
      args = arguments_
      if onError
        $rootScope.$apply ->
          onError.apply that, args

    ), options
  )
]