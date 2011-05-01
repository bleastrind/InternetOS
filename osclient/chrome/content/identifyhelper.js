alert("initializing identifyhelper");
function IdentifyHelper(username,password,baseurl){

	function getResult(url){

		this.ioService = Components.classes["@mozilla.org/network/io-service;1"]
									  .getService(Components.interfaces.nsIIOService);
		var uri = this.ioService.newURI(url, null, null);
		var channel = this.ioService.newChannelFromURI(uri);		
		var aStream = channel.open();
		
		var scriptableInputStream = Components.classes["@mozilla.org/scriptableinputstream;1"]
			.createInstance(Components.interfaces.nsIScriptableInputStream);
		scriptableInputStream.init(aStream);
		
		var token = scriptableInputStream.read(scriptableInputStream.available());
		
		return token;
	}
	this.accessToken = getResult(baseurl+"/loginfortoken?username="+username+"&password="+password);;
}

var gIdentifyHelper = new IdentifyHelper(gConfig.username,gConfig.password,gConfig.baseurl);

alert("get accessToken:" + gIdentifyHelper.accessToken);

var accessTokenAppender =
{
  observe: function(subject, topic, data)
  {
    if (topic == "http-on-modify-request") {
      var httpChannel = subject.QueryInterface(Components.interfaces.nsIHttpChannel);
	  httpChannel.setRequestHeader("token",gIdentifyHelper.accessToken,false);
    }
  },

  get observerService() {
    return Components.classes["@mozilla.org/observer-service;1"]
                     .getService(Components.interfaces.nsIObserverService);
  },

  register: function()
  {
    this.observerService.addObserver(this, "http-on-modify-request", false);
  },

  unregister: function()
  {
    this.observerService.removeObserver(this, "http-on-modify-request");
  }
};
accessTokenAppender.register();
alert("accessTokenAppender added");

alert(gChannel);
