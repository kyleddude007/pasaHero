angular.module("App.controllers", []).controller("MainCtrl", ["$scope", ($scope) ->
  $scope.status = "It works!"
]).controller "ViewCtrl", ["$scope", ($scope) ->
  $scope.status = "Also totally works!"
]