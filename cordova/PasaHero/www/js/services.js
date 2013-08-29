(function() {
  angular.module("App.services", []).factory("cordovaReady", [
    function() {
      return function(fn) {
        var impl, queue;
        queue = [];
        impl = function() {
          return queue.push([].slice.call(arguments_));
        };
        document.addEventListener("deviceready", (function() {
          queue.forEach(function(args) {
            return fn.apply(this, args);
          });
          return impl = fn;
        }), false);
        return function() {
          return impl.apply(this, arguments_);
        };
      };
    }
  ]);

}).call(this);
