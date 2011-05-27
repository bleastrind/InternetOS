var osclient = {
  onLoad: function() {
    // initialization code
    this.initialized = true;
    this.strings = document.getElementById("osclient-strings");
  },

  onMenuItemCommand: function(e) {
    var promptService = Components.classes["@mozilla.org/embedcomp/prompt-service;1"]
                                  .getService(Components.interfaces.nsIPromptService);
    //DEBUG promptService.alert(window, this.strings.getString("helloMessageTitle"), this.strings.getString("helloMessage"));
								
	try{
		

		gCometClient.Begin();
		
		window.alert("success");
	}catch(err){
		window.alert(err);
	}
	
	//window.alert("end");
  },

  onToolbarButtonCommand: function(e) {
    // just reuse the function above.  you can change this, obviously!
    osclient.onMenuItemCommand(e);
  }
};

window.addEventListener("load", function () { osclient.onLoad(); }, false);

