var websocket = null;
var websocket_url = null;
var use_encoder = false;

function init(test, url, description) {
	console.log("init %o, %s, %s", websocket, test, url);
	if ( websocket !== null ) {
		websocket.close();
		websocket = null;
	}
	document.getElementById('messages').innerHTML = '';
	document.getElementById('sampleType').innerHTML = test;
	document.getElementById('sampleDescription').innerHTML = description;

	// Make some elements visible once we've picked a sample
	document.getElementById('simpleForm').removeAttribute("style");
	document.getElementById('sampleOutput').removeAttribute("style");

	// Set the URL, always reset the use_encoder attribute
	websocket_url = "ws://" + window.document.location.host + url;
	use_encoder = false;
	console.log(".. init %s, %s", test, url);
}

function initEncoder(test, url, description) {
	init(test, url, description);
	// In this case, we do want to use the encoder to wrap the data differently
	// This sample does not use a decoder: we instead echo the raw/received JSON to show what the server did.
	use_encoder = true;
}

function send() {
	var json;
	var txt = document.getElementById('inputmessage').value;
	document.getElementById('inputmessage').value='';
	
	if ( txt.indexOf('clear') >= 0) {
		document.getElementById('messages').innerHTML='';
	} else if ( txt === 'client' ) {
		// client alone will initiate a programmatic client in the server
		sendSocket('client:'+websocket_url);
	} else if ( use_encoder ) {
		json = {'content' : txt };
		sendSocket(JSON.stringify(json));
	} else {
		sendSocket(txt);
	}
}

function sendSocket(payload) {
	console.log("sendSocket %o, %s", websocket, websocket_url);
	if ( websocket === null ) {
		websocket = new WebSocket(websocket_url);

		websocket.onerror = function(event) {
			document.getElementById('messages').innerHTML += 'Error: ' + event.data + '<br />';
		}

		websocket.onopen = function(event) {
			document.getElementById('messages').innerHTML += 'Connection established<br />';
			if ( payload ) {
				websocket.send(payload);
			}
		}

		websocket.onclose = function(event) {
			websocket = null;
			document.getElementById('messages').innerHTML += 'Connection closed: ' + event.code + '<br />';
		}

		websocket.onmessage = function(event) {
			document.getElementById('messages').innerHTML += event.data + '<br />';
		}
	} else if ( payload ) {
		websocket.send(payload);
	}

	console.log(".. sendSocket %o, %s", websocket, websocket_url);
	return websocket;
}