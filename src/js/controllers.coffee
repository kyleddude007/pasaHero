controllers = angular.module 'Pasahero.controllers', [
  'leaflet-directive'
  'Pasahero.services'
  'Pasahero.resources'
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

controllers.controller 'FindPathCtrl', ['$scope', 'mapSearchOptions', 'Plan', ($scope, mapSearchOptions, Plan) ->
  $scope.mapSearchOptions = mapSearchOptions

  $scope.plan = Plan.get
    arriveBy: false
    time: '10:25am'
    mode: 'TRANSIT,WALK'
    optimize: 'QUICK'
    maxWalkDistance: 840
    walkSpeed: 1.341
    date: '2013-08-17'
    toPlace: '14.587841,121.056794'
    fromPlace: '14.580972,121.053511'
    , (plan, headers)->

  console.log "adfdsafdsaf"
  console.log $scope.plan
  console.log "adfds"
]

