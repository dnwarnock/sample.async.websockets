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

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class EchoDecoder implements Decoder.Text<EchoObject> {

    @Override
    public EchoObject decode(String msg) throws DecodeException {
        EchoObject o;
        try {
            o = new EchoObject(msg);
        } catch(Exception e) {
            o = new EchoObject(e);
        }
        System.out.println("Decoded " + msg + " -> " + o);
        return o;
    }

    @Override
    public boolean willDecode(String msg) {
        return true;
    }

    @Override
    public void init(EndpointConfig ec) {}

    @Override
    public void destroy() {}
}
