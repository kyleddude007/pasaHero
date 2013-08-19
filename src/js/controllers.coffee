controllers = angular.module 'Pasahero.controllers', [
  'leaflet-directive'
  'Pasahero.services'
  'Pasahero.resources'
]

controllers.controller 'MapCtrl', ['$scope', '$window', 'mapSearchOptions', 'Plan', ($scope, $window, mapSearchOptions, Plan)->
  $scope.mapSearchOptions = mapSearchOptions

  angular.extend $scope,
    markerCenter:
      lat: 14.599512400000000000,
      lng: 120.984219500000000000,
      zoom: 12
    markers: 
      Manila: 
        lat: 14.599512400000000000,
        lng: 120.984219500000000000,
        message: "Drag me to your position",
        focus: true,
        draggable: true
    center:
      lat: 53
      lng: -3
      zoom: 6
    paths:
      p1:
        color: "#008000"
        weight: 3
        latlngs: [
          lat: 14.587841
          lng: 121.056794
        ,
          lat: 14.580972
          lng: 121.053511
        ]

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
      
  ###$scope.plan = Plan.get
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
      console.log $scope.plan
      console.log $scope.plan.plan.from.name
      angular.extend $scope.markers,
        'start':
          lat: $scope.plan.plan.from.lat      
          lng: $scope.plan.plan.from.lon
          draggable: true
          message: $scope.plan.plan.from.name
        'end':
          lat: $scope.plan.plan.to.lat      
          lng: $scope.plan.plan.to.lon
          draggable: true
          message: $scope.plan.plan.to.name
      console.log $scope.markers
     
      console.log $scope.paths   ###   
]

