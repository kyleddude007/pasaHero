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

  services.factory("geolocation", [
    '$rootScope', 'cordovaReady', function($rootScope, cordovaReady) {
      return {
        getCurrentPosition: cordovaReady(function(onSuccess, onError, options) {
          return navigator.geolocation.getCurrentPosition((function() {
            var args, that;
            that = this;
            args = arguments_;
            if (onSuccess) {
              return $rootScope.$apply(function() {
                return onSuccess.apply(that, args);
              });
            }
          }), (function() {
            var args, that;
            that = this;
            args = arguments_;
            if (onError) {
              return $rootScope.$apply(function() {
                return onError.apply(that, args);
              });
            }
          }), options);
        })
      };
    }
  ]);

}).call(this);
