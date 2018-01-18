/*
 * Copyright (c) 2015 Christian Chevalley
 * This file is part of Project Ethercis
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ethercis.ehr.encode.wrappers.json.writer.translator_db2raw;
import com.ethercis.ehr.encode.wrappers.json.I_DvTypeAdapter;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;

/**
 * GSON adapter for ArrayList
 */
public class ArrayListAdapter extends TypeAdapter<ArrayList> implements I_DvTypeAdapter {

	protected AdapterType adapterType = AdapterType.DBJSON2RAWJSON;
	protected String archetypeNodeId = null;
	protected String key = null;

	public ArrayListAdapter(AdapterType adapterType) {
		super();
		this.adapterType = adapterType;
	}

	public ArrayListAdapter(String archetypeNodeId, String key) {
		super();
		this.adapterType = AdapterType.DBJSON2RAWJSON;
		this.archetypeNodeId = archetypeNodeId;
		this.key = key;
	}

	public ArrayListAdapter(String archetypeNodeId) {
		super();
		this.adapterType = AdapterType.DBJSON2RAWJSON;
		this.archetypeNodeId = archetypeNodeId;
	}

	public ArrayListAdapter() {
		super();
		this.adapterType = AdapterType.DBJSON2RAWJSON;
	}

//	@Override
	public ArrayList read(JsonReader arg0) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
	public void write(JsonWriter writer, ArrayList arrayList) throws IOException {
		writer.beginArray();
//		System.out.println("begin array -----------------------------");
		for (Object entry: arrayList){
			if (entry instanceof LinkedTreeMap){
				LinkedTreeMap itemMap = (LinkedTreeMap)entry;
				String path = new PathAttribute().findPath(itemMap);
				String nodeName = "";
				if (path != null)
					nodeName =new PathAttribute(path).nodeNameFromPath(key);
				new LinkedTreeMapAdapter(archetypeNodeId, nodeName).write(writer, itemMap);
			}
			else
				throw new IllegalArgumentException("unhandled item in array:"+entry);
		}
//		System.out.println("end array -----------------------------");
		writer.endArray();
		return;
	}

}
