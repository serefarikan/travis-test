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
package org.openehr.build;

import java.util.HashMap;
import java.util.Map;

/**
 * Enumeration of values that are supplied by the system
 *
 * @author Rong Chen
 * @version 1.0
 */
public enum SystemValue {

    LANGUAGE ("language"),
    CHARSET ("charset"),
    ENCODING ("encoding"),
    TERMINOLOGY_SERVICE ("terminologyService"),
    MEASUREMENT_SERVICE ("measurementService"),
    SUBJECT ("subject"),
    PROVIDER ("provider"),
    COMPOSER ("composer"),
    TERRITORY ("territory"),
    CONTEXT ("context"),
    CATEGORY ("category"),
    UID("uid");

    /* field */
    private final String id;
    private static final Map<String, SystemValue> idMap;
    static {
        SystemValue[] list = { LANGUAGE, CHARSET, TERMINOLOGY_SERVICE,
            MEASUREMENT_SERVICE, SUBJECT, PROVIDER, COMPOSER, TERRITORY, CONTEXT,
            CATEGORY, UID
        };
        idMap = new HashMap<String, SystemValue>();
        for(SystemValue value : list) {
            idMap.put(value.id(), value);
        }
    }

    /* constructor */
    private SystemValue(String id) {
        this.id = id;
    }

    /**
     * Id of this system value
     *
     * @return id
     */
    public String id() {
        return id;
    }

    /**
     * Return system value with matching id
     *
     * @return null if not found
     */
    public static SystemValue fromId(String id) {
        return idMap.get(id);
    }
}

