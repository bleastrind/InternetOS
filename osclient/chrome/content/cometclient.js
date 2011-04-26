var myURLString = gConfig.baseurl + "/clientevent?token="+gIdentifyHelper.accessToken;
alert(myURLString);
var callbackFunc= function (data){
	gListeners.onSignalRecieved(data);//function(d){window.alert(d)};
}
alert("Initializing comet");

function CometClient(url,callback){
	
	
	function StreamListener(aCallbackFunc) {
	  this.mCallbackFunc = aCallbackFunc;
	}
	 
	
	StreamListener.prototype = {
	  mData: "",
	 
	  // nsIStreamListener
	  onStartRequest: function (aRequest, aContext) {
		this.mData = "";
	  },
	  
	  onDataAvailable: function (aRequest, aContext, aStream, aSourceOffset, aLength) {
		var scriptableInputStream =
		  Components.classes["@mozilla.org/scriptableinputstream;1"]
			.createInstance(Components.interfaces.nsIScriptableInputStream);
		scriptableInputStream.init(aStream);
		this.mData += scriptableInputStream.read(aLength);
	  },
	 
	  onStopRequest: function (aRequest, aContext, aStatus) {
	
		if (Components.isSuccessCode(aStatus)) {
		  // request was successfull
		  this.mCallbackFunc(this.mData);
		} else {
		  // request failed
		  this.mCallbackFunc(null);
		}
		try{
			gCometClient.Begin();
		}catch(err){
			alert(err);
		}
	  },
	
	  // nsIChannelEventSink
	  onChannelRedirect: function (aOldChannel, aNewChannel, aFlags) {
		// if redirecting, store the new channel
		gChannel = aNewChannel;
	  },
	 
	  // nsIInterfaceRequestor
	  getInterface: function (aIID) {
		try {
		  return this.QueryInterface(aIID);
		} catch (e) {
		  throw Components.results.NS_NOINTERFACE;
		}
	  },
	 
	  // nsIProgressEventSink (not implementing will cause annoying exceptions)
	  onProgress : function (aRequest, aContext, aProgress, aProgressMax) { },
	  onStatus : function (aRequest, aContext, aStatus, aStatusArg) { },
	 
	  // nsIHttpEventSink (not implementing will cause annoying exceptions)
	  onRedirect : function (aOldChannel, aNewChannel) { },
	 
	  // we are faking an XPCOM interface, so we need to implement QI
	  QueryInterface : function(aIID) {
		if (aIID.equals(Components.interfaces.nsISupports) ||
			aIID.equals(Components.interfaces.nsIInterfaceRequestor) ||
			aIID.equals(Components.interfaces.nsIChannelEventSink) ||
			aIID.equals(Components.interfaces.nsIProgressEventSink) ||
			aIID.equals(Components.interfaces.nsIHttpEventSink) ||
			aIID.equals(Components.interfaces.nsIStreamListener))
		  return this;
		throw Components.results.NS_NOINTERFACE;
	  }
	};


	this.ioService = Components.classes["@mozilla.org/network/io-service;1"]
									  .getService(Components.interfaces.nsIIOService);
	
	this.listener = new StreamListener(callback);
	this.uri = this.ioService.newURI(url, null, null);
	// global channel

	this.getNewChannel = function(){
		var channel = this.ioService.newChannelFromURI(this.uri);
		channel.notificationCallbacks = this.listener; 
		return channel;
	}
	
	this.Begin = function(){
		this.getNewChannel().asyncOpen(this.listener, null);
	}
}
try{
	var gCometClient = new CometClient(myURLString,callbackFunc);
}catch(err){
	alert(err);
}