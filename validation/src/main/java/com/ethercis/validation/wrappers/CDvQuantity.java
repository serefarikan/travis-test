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
import org.openehr.rm.datatypes.quantity.DvQuantity;
import org.openehr.schemas.v1.ARCHETYPECONSTRAINT;
import org.openehr.schemas.v1.CDVQUANTITY;
import org.openehr.schemas.v1.CQUANTITYITEM;
import org.openehr.schemas.v1.IntervalOfReal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by christian on 7/24/2016.
 */
public class CDvQuantity extends CConstraint implements I_CArchetypeConstraintValidate {

    protected CDvQuantity(Map<String, Map<String, String>> localTerminologyLookup) {
        super(localTerminologyLookup);
    }

    public void validate(String path, Object aValue, ARCHETYPECONSTRAINT archetypeconstraint) throws Exception {

        DvQuantity quantity = (DvQuantity)aValue;

        CDVQUANTITY constraint = (CDVQUANTITY) archetypeconstraint;
        //check constraint attributes
        if (quantity.getUnits() == null)
            throw new IllegalArgumentException("No units specified for item:"+quantity+" at path:"+path);

        List<String> stringBuffer = new ArrayList<>();
        match_value:
        {
            if (constraint.sizeOfListArray() == 0) //no units specified in constraint
                break match_value;
            for (CQUANTITYITEM cquantityitem : constraint.getListArray()) {
                //check if this item matches the defined unit
                if (!cquantityitem.getUnits().equals(quantity.getUnits())) {
                    stringBuffer.add(cquantityitem.getUnits());
                    continue;
                }
                if (cquantityitem.isSetMagnitude()) {
                    IntervalOfReal magnitudes = cquantityitem.getMagnitude();
                    IntervalComparator.isWithinBoundaries(new Float(quantity.getMagnitude()), magnitudes);
                }
                if (cquantityitem.isSetMagnitude() && quantity.getMagnitude() != null) {
                    Integer precision = quantity.getPrecision();
                    IntervalComparator.isWithinPrecision(precision, cquantityitem.getPrecision());
                }
                break match_value; //comparison done with a matching unit
            }

            throw new IllegalArgumentException("No matching units for:" + (StringUtils.isNotEmpty(quantity.getUnits()) ? quantity.getUnits() : "*undef*") + ", expected units:" + String.join(",",stringBuffer));
        }
    }
}
