services = angular.module 'Pasahero.services', []

services.factory 'mapSearchOptions', ->
  mapSearchOptions=
    'transit': false
    ,
    'bus': false    
    ,
    'train': false  
    ,
    'walk': false
    ,
    'bycicle': false
