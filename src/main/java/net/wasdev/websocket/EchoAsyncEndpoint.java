/*******************************************************************************
 * Copyright (c) 2015 IBM Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package net.wasdev.websocket;

import java.io.IOException;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/EchoAsyncEndpoint")
public class EchoAsyncEndpoint {


    /** CDI injection of Java EE7 Managed executor service */
    @Resource
    ManagedExecutorService executor;

    @OnOpen
    public void onOpen(Session session, EndpointConfig ec) {
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
    }

    int count = 0;

    @OnMessage
    public void receiveMessage(final String message, final Session session) throws IOException {
        if ( "stop".equals(message) ) {
            session.close();
        } else {
            System.out.println(message + ", " + executor);
            final int id = count++;
            broadcast(session, "Echo " + id + ": " + message);

            executor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                    }
                    broadcast(session, "Delayed " + id + ": " + message);
                    System.out.println("executor -- send " + message);
                }
            });
        }
    }

    private void broadcast(Session session, String message) {
        for (Session s : session.getOpenSessions() ) {
            try {
                s.getBasicRemote().sendText(message);
            } catch (IOException e) {
                try {
                    s.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    @OnError
    public void onError(Throwable t) {
    }
}
