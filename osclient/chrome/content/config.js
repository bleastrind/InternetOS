function Config(){

	var prefs = Components.classes["@mozilla.org/preferences-service;1"].getService(Components.interfaces.nsIPrefBranch);
	this.username = prefs.getCharPref('extensions.osclient.username')||'';
	this.password = prefs.getCharPref('extensions.osclient.password')||'';
	this.baseurl = prefs.getCharPref('extensions.osclient.baseurl')||'';
	
	alert("loading config");
}
gConfig = new Config();