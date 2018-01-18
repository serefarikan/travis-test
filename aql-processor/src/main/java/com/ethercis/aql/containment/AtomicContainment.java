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

/**
 * Simple containment not associated with a Set operator
 * Created by christian on 4/6/2016.
 */
public class AtomicContainment extends AstContainment{

    private AstContainment child;

    public AtomicContainment(Containment containment, AstContainment enclosing){
       super(containment, enclosing);
    }

    public void setChild(AstContainment child){
        this.child = child;
    }

}
