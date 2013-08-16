app = angular.module 'Pasahero',[
  'Pasahero.controllers'
  'Pasahero.directives'
] 

app.config ['$routeProvider', ($routeProvider)->
	$routeProvider
		.when '/',
			templateUrl: 'templates/home.html'
]
