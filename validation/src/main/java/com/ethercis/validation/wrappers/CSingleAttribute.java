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

package com.ethercis.validation.wrappers;

import org.openehr.schemas.v1.ARCHETYPECONSTRAINT;
import org.openehr.schemas.v1.COBJECT;
import org.openehr.schemas.v1.CSINGLEATTRIBUTE;

import java.util.Map;

/**
 * Created by christian on 7/23/2016.
 */
public class CSingleAttribute extends CConstraint implements I_CArchetypeConstraintValidate {

    protected CSingleAttribute(Map<String, Map<String, String>> localTerminologyLookup) {
        super(localTerminologyLookup);
    }

    @Override
    public void validate(String path, Object aValue, ARCHETYPECONSTRAINT archetypeconstraint) throws Exception {

        CSINGLEATTRIBUTE csingleattribute = (CSINGLEATTRIBUTE) archetypeconstraint;

        if (csingleattribute.sizeOfChildrenArray() > 0){
            int rulecount = csingleattribute.sizeOfChildrenArray();
            StringBuffer messageBuffer = new StringBuffer();
            for (COBJECT cobject: csingleattribute.getChildrenArray()) {
                //multiple rules for a specific item, at least one must be verified
                try {
                    new CObject(localTerminologyLookup).validate(path, aValue, cobject);
                } catch (Exception e){
                    if (messageBuffer.length() > 0)
                        messageBuffer.append(", ");
                    messageBuffer.append(e.getMessage());
                    rulecount--;
                }
            }
            if (rulecount == 0){
                throw new IllegalArgumentException(messageBuffer.toString());
            }
        }
    }
}
