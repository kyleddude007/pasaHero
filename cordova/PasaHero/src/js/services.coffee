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
