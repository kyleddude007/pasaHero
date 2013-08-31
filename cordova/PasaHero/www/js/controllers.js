(function() {
  var controllers;

  controllers = angular.module('Pasahero.controllers', ['leaflet-directive', 'Pasahero.services', 'Pasahero.resources']);

  controllers.controller('MainCtrl', [
    '$scope', function($scope) {
      return $scope.status = 'It works!';
    }
  ]);

  controllers.controller('ViewCtrl', [
    '$scope', function($scope) {
      return $scope.status = 'Also totally works!';
    }
  ]);

  controllers.controller('MapCtrl', [
    '$scope', '$window', 'mapSearchOptions', 'Plan', function($scope, $window, mapSearchOptions, Plan) {
      var insertMarker, insertPath, pathCoordinates;
      $scope.mapSearchOptions = mapSearchOptions;
      $scope.instructions = [];
      angular.extend($scope, {
        markerCenter: {
          lat: 14.599512400000000000,
          lng: 120.984219500000000000,
          zoom: 12
        },
        markers: {
          Manila: {
            lat: 14.599512400000000000,
            lng: 120.984219500000000000,
            message: "Drag me to your position",
            focus: true,
            draggable: true
          }
        },
        center: {
          lat: 53,
          lng: -3,
          zoom: 6
        },
        paths: {}
      });
      $scope.events = {
        'contextmenu': function(event) {
          return $scope.$apply(function() {
            return angular.extend($scope.markers, {
              Start: {
                lat: event.latlng.lat,
                lng: event.latlng.lng,
                message: "New",
                focus: false,
                draggable: true
              }
            });
          });
        }
      };
      $scope.plan = Plan.get({
        arriveBy: false,
        time: '10:25am',
        mode: 'TRANSIT,WALK',
        optimize: 'QUICK',
        maxWalkDistance: 840,
        walkSpeed: 1.341,
        date: '2013-08-17',
        toPlace: '14.587841,121.056794',
        fromPlace: '14.580972,121.053511'
      }, function(plan, headers) {
        console.log($scope.plan);
        console.log($scope.plan.plan.from.name);
        insertMarker('Start', $scope.plan.plan.from.lat, $scope.plan.plan.from.lon, true, $scope.plan.plan.from.name);
        insertMarker('End', $scope.plan.plan.to.lat, $scope.plan.plan.to.lon, true, $scope.plan.plan.to.name);
        insertPath('#FF0000', 5, pathCoordinates($scope.plan.plan.itineraries));
        console.log($scope.plan.plan);
        return console.log("adsf");
      });
      insertMarker = function(name, lat, lng, draggable, message) {
        if ($scope.markers == null) {
          $scope.markers = {};
        }
        return angular.extend($scope.markers, {
          name: {
            'lat': lat,
            'lng': lng,
            'draggable': draggable,
            'message': message
          }
        });
      };
      insertPath = function(color, weight, latlngs) {
        if ($scope.paths == null) {
          $scope.paths = {};
        }
        angular.extend($scope.paths, {
          p1: {
            'color': color,
            'weight': weight,
            'latlngs': latlngs
          }
        });
        console.log("inset");
        return console.log($scope);
      };
      return pathCoordinates = function(itineraries) {
        var i, latlngs, leg, legs, _i, _j, _len, _len1;
        for (_i = 0, _len = itineraries.length; _i < _len; _i++) {
          i = itineraries[_i];
          latlngs = [];
          legs = i.legs;
          for (_j = 0, _len1 = legs.length; _j < _len1; _j++) {
            leg = legs[_j];
            latlngs.push({
              'lat': leg.from.lat,
              'lng': leg.from.lon
            });
            latlngs.push({
              'lat': leg.to.lat,
              'lng': leg.to.lon
            });
          }
        }
        return latlngs;
      };
    }
  ]);

  controllers.controller("Map2Ctrl", [
    '$scope', 'geolocation', function($scope, geolocation) {
      return geolocation.getCurrentPosition(function(position) {
        return alert("Latitude: " + position.coords.latitude + "\n" + "Longitude: " + position.coords.longitude + "\n" + "Altitude: " + position.coords.altitude + "\n" + "Accuracy: " + position.coords.accuracy + "\n" + "Altitude Accuracy: " + position.coords.altitudeAccuracy + "\n" + "Heading: " + position.coords.heading + "\n" + "Speed: " + position.coords.speed + "\n" + "Timestamp: " + position.timestamp + "\n");
      });
    }
  ]);

}).call(this);
