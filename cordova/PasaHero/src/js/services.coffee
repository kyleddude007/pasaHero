angular.module("App.services", []).factory "cordovaReady", [->
  (fn) ->
    queue = []
    impl = ->
      queue.push [].slice.call(arguments_)

    document.addEventListener "deviceready", (->
      queue.forEach (args) ->
        fn.apply this, args

      impl = fn
    ), false
    ->
      impl.apply this, arguments_
]