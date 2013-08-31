(function() {
  var directives;

  directives = angular.module('Pasahero.directives', []);

  directives.directive('psPlanner', function() {
    return {
      restrict: 'A',
      link: function(scope, element, attrs) {
        return console.log("From directive: ");
      }
    };
  });

}).call(this);
