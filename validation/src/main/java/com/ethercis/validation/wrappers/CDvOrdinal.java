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

import org.apache.commons.lang.StringUtils;
import org.openehr.rm.datatypes.quantity.DvOrdinal;
import org.openehr.schemas.v1.ARCHETYPECONSTRAINT;
import org.openehr.schemas.v1.CDVORDINAL;
import org.openehr.schemas.v1.DVORDINAL;

import java.util.Map;

/**
 * Created by christian on 7/24/2016.
 */
public class CDvOrdinal extends CConstraint  implements I_CArchetypeConstraintValidate {

    protected CDvOrdinal(Map<String, Map<String, String>> localTerminologyLookup) {
        super(localTerminologyLookup);
    }

    @Override
    public void validate(String path, Object aValue, ARCHETYPECONSTRAINT archetypeconstraint) throws Exception {

        if (!(aValue instanceof DvOrdinal))
            throw new IllegalArgumentException("INTERNAL: argument is not a DvOrdinal");

        DvOrdinal dvOrdinal = (DvOrdinal)aValue;
        CDVORDINAL cdvordinal = (CDVORDINAL)archetypeconstraint;

        match_loop:
        {
            if (cdvordinal.sizeOfListArray() > 0) {
                for (DVORDINAL ordinal : cdvordinal.getListArray()) {
                    if (ordinal.getValue() == dvOrdinal.getValue()) {
                        //check symbol
                        if (StringUtils.isNotEmpty(ordinal.getSymbol().getValue()) && !(ordinal.getSymbol().getValue().equals(dvOrdinal.getSymbolValue())))
                            continue ;
                        String codeString = dvOrdinal.getSymbol().getDefiningCode().getCodeString();
                        String terminology = dvOrdinal.getSymbol().getDefiningCode().getTerminologyId().name();

                        if (!(StringUtils.isNotEmpty(codeString) && ordinal.getSymbol().getDefiningCode().getCodeString().equals(codeString))
                                &&
                                (StringUtils.isNotEmpty(terminology) && ordinal.getSymbol().getDefiningCode().getTerminologyId().getValue().equals(terminology)))
                            continue ;
                        break match_loop;
                    }
                }
                throw new IllegalArgumentException("DvOrdinal does not match any valid value, ordinal value:" + dvOrdinal.getValue() + ", code:'" + dvOrdinal.getSymbol() + "'");
            }
        }
    }
}
