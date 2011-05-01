
var Tool = {
	createChannel:function(url){
		var ioService = Components.classes["@mozilla.org/network/io-service;1"]
            .getService(Components.interfaces.nsIIOService);
		var uri = ioService.newURI(url, null, null);
		var channel = ioService.newChannelFromURI(uri);
		return channel;
	},
	call: function(url){
		var channel = Tool.createChannel(url);
		return channel.open();
	},
	
	postData: function(url,postData){
		var channel = Tool.createChannel(url);
		
		var inputStream = Components.classes["@mozilla.org/io/string-input-stream;1"]
                  .createInstance(Components.interfaces.nsIStringInputStream);
		inputStream.setData(postData, postData.length);
		var uploadChannel = channel.QueryInterface(Components.interfaces.nsIUploadChannel);
		uploadChannel.setUploadStream(inputStream, "application/stream", -1);

		//uploadChannel.requestMethod = "POST";
		
		return channel.open();
	},
	
	ayncCall: function(url,listener){
		var channel = Tool.createChannel(url);
		channel.asyncOpen(listener,null);
	},
	
	ayncPostData: function(url,postData){		
		var channel = Tool.createChannel(url);
		
		var inputStream = Components.classes["@mozilla.org/io/string-input-stream;1"]
                  .createInstance(Components.interfaces.nsIStringInputStream);
		inputStream.setData(postData, postData.length);
		
		var uploadChannel = channel.QueryInterface(Components.interfaces.nsIUploadChannel);
		uploadChannel.setUploadStream(inputStream, "application/stream", -1);
		
		var httpChannel = channel.QueryInterface(Components.interfaces.nsIHttpChannel)
		httpChannel.setRequestHeader("async",true,false);
		//uploadChannel.requestMethod = "POST";
		
		channel.asyncOpen({},null);
	},
	
	alertObj: function(obj){
		var output = '';
		for (property in obj) {
			output += property + ': ' + obj[property]+'; ';
		}
		alert(output);
	}
}