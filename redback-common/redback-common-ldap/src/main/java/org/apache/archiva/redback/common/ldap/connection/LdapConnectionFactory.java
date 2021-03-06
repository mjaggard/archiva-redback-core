package org.apache.archiva.redback.common.ldap.connection;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import javax.naming.spi.ObjectFactory;
import javax.naming.spi.StateFactory;


/**
 * @author <a href="mailto:trygvis@inamo.no">Trygve Laugst&oslash;l</a>
 */
public interface LdapConnectionFactory
{
    LdapConnection getConnection()
        throws LdapException;

    LdapConnection getConnection( LdapConnectionConfiguration ldapConnectionConfiguration )
        throws LdapException;

    LdapConnection getConnection( Rdn subRdn )
        throws LdapException;

    LdapConnection getConnection( String bindDn, String password )
        throws LdapException;

    LdapName getBaseDnLdapName()
        throws LdapException;

    void addObjectFactory( Class<? extends ObjectFactory> objectFactoryClass );

    void addStateFactory( Class<? extends StateFactory> objectFactoryClass );

    void initialize();

}
