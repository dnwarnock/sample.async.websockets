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

import java.io.StringReader;
import java.util.concurrent.atomic.AtomicInteger;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;

public class EchoObject {
	static final AtomicInteger count = new AtomicInteger();

	final JsonObject obj;

	public EchoObject(String msg) {
		JsonReader r = Json.createReader(new StringReader(msg));
		JsonObject in = r.readObject();

		JsonObjectBuilder b = Json.createObjectBuilder();
		b.add("count", count.getAndIncrement());
		b.add("content", in.getString("content", "none provided"));
		obj = b.build();
	}

	public String toString() {
		return obj.toString();
	}

	public EchoObject(Exception e) {
		JsonObjectBuilder b = Json.createObjectBuilder();
		b.add("content", e.toString());
		b.add("count", -1);
		obj = b.build();
	}

	public boolean stopRequest() {
		return "stop".equals(obj.getString("content"));
	}
}
