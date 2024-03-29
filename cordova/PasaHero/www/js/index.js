(function() {
  var app;

  app = {
    initialize: function() {
      return this.bindEvents();
    },
    bindEvents: function() {
      return document.addEventListener("deviceready", this.onDeviceReady, false);
    },
    onDeviceReady: function() {
      return app.receivedEvent("deviceready");
    },
    receivedEvent: function(id) {
      var listeningElement, parentElement, receivedElement;
      parentElement = document.getElementById(id);
      listeningElement = parentElement.querySelector(".listening");
      receivedElement = parentElement.querySelector(".received");
      listeningElement.setAttribute("style", "display:none;");
      receivedElement.setAttribute("style", "display:block;");
      return console.log("Received Event: " + id);
    }
  };

}).call(this);
