(function() {
  var services;

  services = angular.module('Pasahero.services', []);

  services.factory('cordovaReady', [
    function() {
      return function(fn) {
        var impl, queue;
        queue = [];
        impl = function() {
          return queue.push([].slice.call(arguments_));
        };
        document.addEventListener('deviceready', (function() {
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

  services.factory('mapSearchOptions', function() {
    var mapSearchOptions;
    return mapSearchOptions = {
      'transit': false,
      'bus': false,
      'train': false,
      'walk': false,
      'bycicle': false
    };
  });

}).call(this);
