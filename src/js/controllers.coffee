controllers = angular.module 'Pasahero.controllers', [
  'leaflet-directive'
  'Pasahero.services'
]

controllers.controller 'MapCtrl', ['$scope', '$window', ($scope, $window)->
  angular.extend $scope,
    markerCenter:
      lat: 14.599512400000000000,
      lng: 120.984219500000000000,
      zoom: 11
    ,
    markers: 
      Manila: 
        lat: 14.599512400000000000,
        lng: 120.984219500000000000,
        message: "Drag me to your position",
        focus: true,
        draggable: true

  $scope.events =
    'contextmenu': (event)->
      $scope.$apply ->
        angular.extend $scope.markers,
          Start:
            lat: event.latlng.lat 
            lng: event.latlng.lng 
            message: "New"
            focus: false
            draggable: true
      
]

controllers.controller 'FindPathCtrl', ['$scope', 'mapSearchOptions', ($scope, mapSearchOptions) ->
  $scope.mapSearchOptions = mapSearchOptions
]

