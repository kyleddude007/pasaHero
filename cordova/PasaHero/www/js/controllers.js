(function() {
  angular.module("App.controllers", []).controller("MainCtrl", [
    "$scope", function($scope) {
      return $scope.status = "It works!";
    }
  ]).controller("ViewCtrl", [
    "$scope", function($scope) {
      return $scope.status = "Also totally works!";
    }
  ]);

}).call(this);
