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
package com.ethercis.dao.access.interfaces;

import com.ethercis.dao.access.jooq.SystemAccess;

import java.util.UUID;

import static com.ethercis.jooq.pg.Tables.SYSTEM;

/**
 * System access layer interface
 * Created by Christian Chevalley on 4/21/2015.
 */
public interface I_SystemAccess extends I_SimpleCRUD<I_SystemAccess, UUID> {

    public static I_SystemAccess getInstance(I_DomainAccess domainAccess, String description, String  settings){
        return new SystemAccess(domainAccess, description, settings);
    }

    /**
     * retrieve a system entry by its Id
     * @param domainAccess SQL access
     * @param id UUID
     * @return UUID
     * @throws Exception
     */
    public static I_SystemAccess retrieveInstance(I_DomainAccess domainAccess, UUID id) throws Exception {
        return SystemAccess.retrieveInstance(domainAccess, id);
    }

    /**
     * retrieve the Id of a system by name (or settings)
     * @param domainAccess SQL access
     * @param settings a string describing the system (arbitrary convention)
     * @return UUID or null if not found
     * @throws Exception
     */
    public static UUID retrieveInstanceId(I_DomainAccess domainAccess, String settings) throws Exception {
        return SystemAccess.retrieveInstanceId(domainAccess, settings);
    }

    /**
     * Helper to retrieve or storeComposition a local host identifier<br>
     * the local settings is a combination of MAC address and hostname:<br>
     * for example: 44-87-FC-A9-B4-B2|TEST-PC<br>
     * if the system is not yet in the DB it is created, it is retrieved otherwise
     * @return UUID of local system from DB
     * @throws Exception
     */
    public static UUID createOrRetrieveLocalSystem(I_DomainAccess domainAccess) throws Exception{
        return SystemAccess.createOrRetrieveLocalSystem(domainAccess);
    }

    UUID getId();

    public static Integer delete(I_DomainAccess domainAccess, UUID id){
        return domainAccess.getContext().delete(SYSTEM).where(SYSTEM.ID.eq(id)).execute();
    }

    String getSettings();

    String getDescription();
}
