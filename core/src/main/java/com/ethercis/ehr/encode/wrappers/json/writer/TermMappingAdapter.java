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

package com.ethercis.ehr.encode.wrappers.json.writer;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import org.openehr.rm.datatypes.text.DvCodedText;
import org.openehr.rm.datatypes.text.TermMapping;

import java.io.IOException;
import java.util.List;

/**
 * Created by christian on 4/3/2017.
 */
public class TermMappingAdapter extends DvTypeAdapter<DvCodedText> {

    private Gson gson;

    public TermMappingAdapter() {
    }


    public void write(JsonWriter writer, List<TermMapping> termMappings) throws IOException {
        if (termMappings == null) {
//            writer.nullValue();
            return;
        }

        if (termMappings.size() > 0){
            DvCodedTextAdapter dvCodedTextAdapter = new DvCodedTextAdapter(); //used to encode DV_CODED_TEXT in mappings
            CodePhraseAdapter codePhraseAdapter = new CodePhraseAdapter();
            writer.name("mappings");
            writer.beginArray();// [
            for (TermMapping termMapping: termMappings){
                writer.beginObject(); //{
                writer.name("match").value(termMapping.getMatch().getValue());
                writer.name("purpose");
                dvCodedTextAdapter.write(writer, termMapping.getPurpose());
                writer.name("target");
                codePhraseAdapter.write(writer, termMapping.getTarget());
                writer.endObject(); //}
            }
            writer.endArray(); // ]
        }
    }
}
