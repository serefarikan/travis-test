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

import org.apache.xmlbeans.impl.values.XmlAnyTypeImpl;
import org.openehr.schemas.v1.*;

import java.util.Map;

/**
 * Created by christian on 7/23/2016.
 */
public class CDomainType extends CConstraint implements I_CArchetypeConstraintValidate {

    protected CDomainType(Map<String, Map<String, String>> localTerminologyLookup) {
        super(localTerminologyLookup);
    }

    @Override
    public void validate(String path, Object aValue, ARCHETYPECONSTRAINT archetypeconstraint) throws Exception {

        if (archetypeconstraint instanceof CDVORDINAL)
            new CDvOrdinal(localTerminologyLookup).validate(path, aValue, archetypeconstraint);
        else if (archetypeconstraint instanceof CCODEPHRASE)
            new CCodePhrase(localTerminologyLookup).validate(path, aValue, archetypeconstraint);
        else if (archetypeconstraint instanceof CDVQUANTITY)
            new CDvQuantity(localTerminologyLookup).validate(path, aValue, archetypeconstraint);
        else if (archetypeconstraint instanceof CDVSTATE)
            new CDvState(localTerminologyLookup).validate(path, aValue, archetypeconstraint);
        else
            throw new IllegalArgumentException("INTERNAL: unsupported CDOMAINTYPE:"+archetypeconstraint);

    }
}
