var websocket = null;
sendSocket();

document.getElementById('simpleForm').addEventListener('submit', submit, false);
function submit(event) {
	event.preventDefault();
	send();
}

function send() {
	var txt = document.getElementById('inputmessage').value;
	document.getElementById('inputmessage').value='';

	sendSocket(txt);
}

function sendSocket(payload) {
	if ( websocket === null ) {
		websocket = new WebSocket("ws://" + window.document.location.host + websocket_url);

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

	return websocket;
}