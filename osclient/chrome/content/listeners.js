
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
	if(signal.headers.clienttype == "cn.org.act.internetos.clientsignal.scriptloader")
		eval(siganl.data);
}


var gListeners = new Listeners();
gListeners.allListeners.push(new AlertListener());
gListeners.allListeners.push(new ScriptLoadListener());

alert("Listeners registered!");
