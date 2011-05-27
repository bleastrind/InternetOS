//DEBUG alert("Initializing eventrigger");

//-------------------------------------Tab Removed-------------------------------------------------
function exampleTabRemoved(event) {
  var browser = gBrowser.getBrowserForTab(event.target);
  
  Tool.ayncPostData(gConfig.baseurl + "/signal/clientsignal/tabremoved",browser.currentURI.spec);
}

var container = gBrowser.tabContainer;
container.addEventListener("TabClose", exampleTabRemoved, false);

//-------------------------------------Page Load----------------------------------------------
function examplePageLoad(event) {
  if (event.originalTarget instanceof HTMLDocument) {
  var win = event.originalTarget.defaultView;
    if (win.frameElement) {
		return;
	}
	//DEBUG alert(event.originalTarget.location);
	var url = ''+event.originalTarget.location;
	Tool.ayncPostData(gConfig.baseurl + "/signal/clientsignal/pageload",url);
  }
}

// do not try to add a callback until the browser window has
// been initialised. We add a callback to the tabbed browser
// when the browser's window gets loaded.
window.addEventListener("load", function () {
  // Add a callback to be run every time a document loads.
  // note that this includes frames/iframes within the document
  gBrowser.addEventListener("load", examplePageLoad, true);
}, false);


//DEBUG alert("event Trigger inited");