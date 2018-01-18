/*
 * Copyright (c) Ripple Foundation CIC Ltd, UK, 2017
 * Author: Christian Chevalley
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

package com.ethercis.aql.definition;

import java.util.List;

/**
 * Created by christian on 9/20/2017.
 */
public class Variables {

    private List<I_VariableDefinition> definitionList;

    public Variables(List<I_VariableDefinition> definitionList) {
        this.definitionList = definitionList;
    }

    public boolean hasDefinedDistinct(){
        for (I_VariableDefinition variableDefinition: definitionList){
            if (variableDefinition.isDistinct())
                return true;
        }
        return false;
    }

    public boolean hasDefinedFunction(){
        for (I_VariableDefinition variableDefinition: definitionList){
            if (variableDefinition.isFunction())
                return true;
        }
        return false;
    }
}
