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

package com.ethercis.aql.containment;

import org.apache.commons.collections4.set.ListOrderedSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Define the set of containments for a CONTAINS clause
 * <p>
 * Containment sets are associated with Set (boolean) operators and their relation with an enclosing set
 * (inclusion). This structure define the set operations required at the query layer implementation. For
 * example, in an SQL context, this will define set operation like: INTERSECT, UNION, EXCEPT.
 * </p>
 * Created by christian on 4/12/2016.
 */
public class ContainmentSet {

    private int serial; //for debugging purpose only
    private Containment enclosing;
    private ContainmentSet parentSet;
    private ListOrderedSet<Object> containmentList = new ListOrderedSet<>();
    private int operatorSlot = -1; //the last position where to insert an operator, -1 initially


    public enum OPERATOR {AND, OR, XOR}

    public ContainmentSet(int serial, Containment enclosing) {
        this.serial = serial;
        this.enclosing = enclosing;
    }

    public void add(Containment containment){
        containmentList.add(containment);
    }

    public void add(String operator){
        containmentList.add(OPERATOR.valueOf(operator));
    }


    private boolean isContainmentInList(ListOrderedSet<Object> list, Containment containment){
        for (Object item: list){
            if (item instanceof Containment){
                if (((Containment)item).equals(containment))
                    return true;
            }
        }
        return false;
    }

    public void setOperator(String operator){
        OPERATOR op = OPERATOR.valueOf(operator.trim());

        if (op == null)
            throw new IllegalArgumentException("Invalid operator value:"+operator);

//        this.operator = op;

        //insert the operator in the list INFIX
        if (operatorSlot < 0) {
            //traverse the containment list to identify an item without an explicit containedIn in the same list
            //CONTAINS is an operator...
            for (int i = containmentList.size() -1; i >= 0; i--){
                Object item = containmentList.get(i);
                if (item instanceof Containment){
                    Containment containment = (Containment)item;
                    if (containment.getEnclosingContainment()!= null){
                        Containment enclosing = containment.getEnclosingContainment();
                        if (!isContainmentInList(containmentList, enclosing)){
                            operatorSlot = i;
                            break;
                        }
                    }
                    else {
                        operatorSlot = i;
                        break;
                    }
                }

            }
//            operatorSlot = containmentList.size() - 1;
        }
        else
            operatorSlot -= 1;

//        operatorSlot = containmentList.size() - 1;

        if (operatorSlot < 0)
            throw new IllegalArgumentException("Cannot insert operator:"+op);

        containmentList.add(operatorSlot, op);
    }

    public String toString(){
        StringBuffer sb = new StringBuffer();

        sb.append(serial+"|");

        if (containmentList.size() > 0) {
            boolean comma = false;
            for (Object item : containmentList) {
                if (comma)
                    sb.append(",");

                comma = true;

                if (item instanceof Containment)
                    sb.append(item);
                else if (item instanceof OPERATOR) {
                    sb.append(item);
                } else if (item instanceof String) {
                    sb.append(item);
                } else
                    sb.append("-- Unhandled Item Type --");

            }
        }
        else
            sb.append("--EMPTY SET--");

        if (parentSet != null)
            sb.append("<<<IN PARENT#"+parentSet.serial);
        else
            sb.append("<<< ROOT");
        return sb.toString();
    }

    public int size(){
        return containmentList.size();
    }

    public boolean isEmpty(){
        return  (containmentList.size() == 0 && enclosing.getSymbol() == null && enclosing.getClassName() == null);
    }

    public void setParentSet(ContainmentSet parentSet) {
        this.parentSet = parentSet;
    }

    public ListOrderedSet<Object> getContainmentList() {
        return containmentList;
    }

    public ContainmentSet getParentSet() {
        return parentSet;
    }

    public Containment getEnclosing() {
        return enclosing;
    }
}
