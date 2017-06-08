# Java EE7: WebSockets [![Build Status](https://travis-ci.org/WASdev/sample.async.websockets.svg?branch=master)](https://travis-ci.org/WASdev/sample.async.websockets)

The [WebSockets standard](#more-on-websockets) defines a full-duplex communication protocol to simplify and streamline long-running communications between a client and a server. The protocol has a well-defined wire format that allows for text or binary messages to be interleaved at will: either side of the connection can send messages at any time, in any order (which is a significant difference from the requirements of Comet or other long-polling mechanisms which require management of several connections to emulate bidirectional communication). The wire format is compact and efficient, making it ideal for small messages.

A WebSocket connection is established by upgrading an existing HTTP connection via an Upgrade handshake. The connection continues to use the original HTTP connection after upgrade, which allows it to work with firewalls and other infrastructure optimized for HTTP traffic; however, HTTP [proxy servers may need to be upgraded to understand the WebSocket protocol](https://en.wikipedia.org/wiki/WebSocket#Proxy_traversal), especially those that do SSL termination.

This sample contains a few variations to illustrate how to use WebSockets in Java EE7 applications. It goes hand-in-hand with the [Introduction to WebSockets](http://www.slideshare.net/wasdevnet/introduction-to-websockets-51912798) session from IBM InterConnect 2015.

* *Simple Annotated WebSocket*: [SimpleEndpoint](/async-websocket-application//src/main/java/net/wasdev/websocket/SimpleEndpoint.java) provides a simple example of an annotated endpoint
* *Simple Programmatic WebSocket*: [ProgrammaticEndpoint](/async-websocket-application//src/main/java/net/wasdev/websocket/ProgrammaticEndpoint.java) defines an endpoint that is configured via the [EndpointApplicationConfig](/async-websocket-application//src/main/java/net/wasdev/websocket/EndpointApplicationConfig.java)
* *Echo sample*: [EchoEndpoint](/async-websocket-application//src/main/java/net/wasdev/websocket/EchoEndpoint.java) also uses annotations, but provides an example of simple message broadcasting via WebSockets.
* *Echo async sample*: [EchoAsyncEndpoint](/async-websocket-application//src/main/java/net/wasdev/websocket/EchoAsyncEndpoint.java) adds an asynchronous element to the simple broadcast example, using a CDI-injected executor to provide a delayed rebroadcast.
* *Echo sample with Encoder/Decoder*: [EchoEncoderEndpoint](/async-websocket-application//src/main/java/net/wasdev/websocket/EchoAsyncEndpoint.java) extends the basic broadcast example with a simple [Decoder](/async-websocket-application/src/main/java/net/wasdev/websocket/EchoDecoder.java) and [Encoder](/async-websocket-application/src/main/java/net/wasdev/websocket/EchoEncoder.java).

This sample also includes a WebSocket client, [AnnotatedClientEndpoint](/async-websocket-application//src/main/java/net/wasdev/websocket/AnnotatedClientEndpoint.java), which can be brought into the conversation with either the *Echo sample* or the *Echo async sample* by providing `client` as input.

## Getting Started

Browse the code to see what it does, or build and run it yourself:

* [Building and running on the command line](/docs/Using-cmd-line.md)
* [Building and running using Eclipse and WebSphere Development Tools (WDT)](/docs/Using-WDT.md)

Once the server has been started, go to [http://localhost:9082/websocket/](http://localhost:9082/websocket/) to interact with the sample. Cross-reference the source to understand what the client side (Java or JavaScript) and server side (Java) are doing.

## More on WebSockets
* [Wikipedia summary](https://en.wikipedia.org/wiki/WebSocket)
* [WebSockets: Proxy Traversal](https://en.wikipedia.org/wiki/WebSocket#Proxy_traversal)
* [w3 JavaScript WebSockets API](http://www.w3.org/TR/websockets/#contents)
* [WebSocket protocol: RFC 6455](http://tools.ietf.org/html/rfc6455)
* [JSR 356: Java API for WebSocket](https://jcp.org/en/jsr/detail?id=356)

## Notice

© Copyright IBM Corporation 2015, 2017.

## License

```text
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
````
