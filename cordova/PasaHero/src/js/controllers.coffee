controllers = angular.module 'Pasahero.controllers', [
  'leaflet-directive'
  'Pasahero.services'
  'Pasahero.resources'
]

controllers.controller 'MainCtrl', ['$scope', ($scope) ->
  $scope.status = 'It works!'
]

controllers.controller 'ViewCtrl', ['$scope', ($scope) ->
  $scope.status = 'Also totally works!'
]

controllers.controller 'MapCtrl', ['$scope', '$window', 'mapSearchOptions', 'Plan', ($scope, $window, mapSearchOptions, Plan)->
  $scope.mapSearchOptions = mapSearchOptions
  $scope.instructions = []

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
    paths:{}


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
      console.log $scope.plan
      console.log $scope.plan.plan.from.name
      insertMarker 'Start', $scope.plan.plan.from.lat, $scope.plan.plan.from.lon, true, $scope.plan.plan.from.name
      insertMarker 'End', $scope.plan.plan.to.lat, $scope.plan.plan.to.lon, true, $scope.plan.plan.to.name
      insertPath '#FF0000', 5, pathCoordinates($scope.plan.plan.itineraries) 
      console.log $scope.plan.plan     
      console.log "adsf"

  insertMarker = (name, lat, lng, draggable, message)->
    if !$scope.markers?
      $scope.markers = {}
    angular.extend $scope.markers,
      name:
        'lat': lat
        'lng': lng
        'draggable': draggable
        'message': message

  insertPath = (color, weight, latlngs)->
    if !$scope.paths?
      $scope.paths = {}  
    angular.extend $scope.paths,
      p1:
        'color': color
        'weight': weight
        'latlngs': latlngs
    console.log "inset"
    console.log $scope

  pathCoordinates =(itineraries)->
    for i in itineraries
      latlngs = []
      legs = i.legs
      for leg in legs
        latlngs.push 
          'lat': leg.from.lat
          'lng': leg.from.lon
        latlngs.push 
          'lat': leg.to.lat
          'lng': leg.to.lon
    latlngs
]

controllers.controller "Map2Ctrl", ['$scope', 'geolocation', ($scope, geolocation) ->
  geolocation.getCurrentPosition (position) ->
    alert "Latitude: " + position.coords.latitude + "\n" + "Longitude: " + position.coords.longitude + "\n" + "Altitude: " + position.coords.altitude + "\n" + "Accuracy: " + position.coords.accuracy + "\n" + "Altitude Accuracy: " + position.coords.altitudeAccuracy + "\n" + "Heading: " + position.coords.heading + "\n" + "Speed: " + position.coords.speed + "\n" + "Timestamp: " + position.timestamp + "\n"
]

  


