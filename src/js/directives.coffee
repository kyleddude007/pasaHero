directives = angular.module 'Pasahero.directives', []

directives.directive 'psPlanner', ->
  restrict: 'A'
  link: (scope, element, attrs) ->
    console.log "From directive: "
    