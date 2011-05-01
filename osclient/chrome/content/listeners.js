
function Listeners(){
	this.allListeners = [];
	this.onSignalRecieved = function(signalstring){
		alert(signalstring);
		try{
			var signal = eval("("+signalstring+")");

			for(var i in this.allListeners){
				this.allListeners[i].recieve(signal);
			}
		}catch(err){
			alert(err);
		}
	}
}

function AlertListener(){}

AlertListener.prototype.recieve = function(signal){
	alert(signal.headers[0].clienttype);
	if(signal.headers[0].clienttype == "cn.org.act.internetos.clientsignal.alert")
		alert(signal.data);
}

function ScriptLoadListener(){}

ScriptLoadListener.prototype.recieve = function(signal){
	if(signal.headers.clienttype == "osclient://internetos/clientsignal/scriptloader")
		eval(siganl.data);
}

function TabControlListener(){}

TabControlListener.prototype.recieve = function(signal){
	if(signal.headers.clienttype == "osclient://internetos/clientsignal/tabcontrol"){
		var data = eval("("+signal.data+")");
		if(data.type == "openTab"){
			var tab = gBrowser.addTab(data.taburl);
			gBroswer.selectedTab = tab;
		}
	}
}

var gListeners = new Listeners();
gListeners.allListeners.push(new AlertListener());
gListeners.allListeners.push(new ScriptLoadListener());
gListeners.allListeners.push(new TabControlListener());

alert("Listeners registered!");
