
function Listeners(){
	this.listeners = [];
	this.onSignalRecieved = function(signalstring){
		alert(signalstring);
		try{
			var signal = eval("("+signalstring+")");
			for(var l in this.listeners){
				l.recieve(signal);
			}
		}catch(err){
			alert(err);
		}
	}
}

function AlertListener(){}

AlertListener.prototype.recieve = function(signal){
	if(signal.headers.clienttype == "cn.org.act.internetos.clientsignal.alert")
		alert(siganl.data);
}

function ScriptLoadListener(){}

ScriptLoadListener.prototype.recieve = function(signal){
	if(signal.headers.clienttype == "cn.org.act.internetos.clientsignal.scriptloader")
		eval(siganl.data);
}


var gListeners = new Listeners();
gListeners.listeners.push(new AlertListener());
gListeners.listeners.push(new ScriptLoadListener());

alert("Listeners registered!");