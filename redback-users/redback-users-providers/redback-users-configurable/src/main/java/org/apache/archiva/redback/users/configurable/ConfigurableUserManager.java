package org.apache.archiva.redback.users.configurable;

/*
 * Copyright 2001-2007 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.archiva.redback.configuration.UserConfiguration;
import org.apache.archiva.redback.configuration.UserConfigurationKeys;
import org.apache.archiva.redback.users.AbstractUserManager;
import org.apache.archiva.redback.users.User;
import org.apache.archiva.redback.users.UserManager;
import org.apache.archiva.redback.users.UserManagerException;
import org.apache.archiva.redback.users.UserNotFoundException;
import org.apache.archiva.redback.users.UserQuery;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * @author <a href="jesse@codehaus.org"> jesse
 */
@Service( "userManager#configurable" )
public class ConfigurableUserManager
    extends AbstractUserManager
    implements UserManager
{
    @Inject
    @Named( value = "userConfiguration#default" )
    private UserConfiguration config;

    @Inject
    private ApplicationContext applicationContext;

    private UserManager userManagerImpl;


    @PostConstruct
    public void initialize()
    {
        String userManagerRole = config.getString( UserConfigurationKeys.USER_MANAGER_IMPL );

        if ( userManagerRole == null )
        {
            throw new RuntimeException( "User Manager Configuration Missing: " + UserConfigurationKeys.USER_MANAGER_IMPL
                                            + " configuration property" );
        }

        log.info( "use userManager impl with key: '{}'", userManagerRole );

        userManagerImpl = applicationContext.getBean( "userManager#" + userManagerRole, UserManager.class );
    }

    public User addUser( User user )
        throws UserManagerException
    {
        return userManagerImpl.addUser( user );
    }

    public void addUserUnchecked( User user )
        throws UserManagerException
    {
        userManagerImpl.addUserUnchecked( user );
    }

    public User createUser( String username, String fullName, String emailAddress )
        throws UserManagerException
    {
        return userManagerImpl.createUser( username, fullName, emailAddress );
    }

    public UserQuery createUserQuery()
    {
        return userManagerImpl.createUserQuery();
    }

    public void deleteUser( String username )
        throws UserNotFoundException, UserManagerException
    {
        userManagerImpl.deleteUser( username );
    }

    public void eraseDatabase()
    {
        userManagerImpl.eraseDatabase();
    }

    public User findUser( String username )
        throws UserManagerException, UserNotFoundException
    {
        return userManagerImpl.findUser( username );
    }

    @Override
    public User findUser( String username, boolean useCache )
        throws UserNotFoundException, UserManagerException
    {
        return userManagerImpl.findUser( username, useCache );
    }

    @Override
    public User getGuestUser()
        throws UserNotFoundException, UserManagerException
    {
        return userManagerImpl.getGuestUser();
    }

    public List<User> findUsersByEmailKey( String emailKey, boolean orderAscending )
        throws UserManagerException
    {
        return userManagerImpl.findUsersByEmailKey( emailKey, orderAscending );
    }

    public List<User> findUsersByFullNameKey( String fullNameKey, boolean orderAscending )
        throws UserManagerException
    {
        return userManagerImpl.findUsersByFullNameKey( fullNameKey, orderAscending );
    }

    public List<User> findUsersByQuery( UserQuery query )
        throws UserManagerException
    {
        return userManagerImpl.findUsersByQuery( query );
    }

    public List<User> findUsersByUsernameKey( String usernameKey, boolean orderAscending )
        throws UserManagerException
    {
        return userManagerImpl.findUsersByUsernameKey( usernameKey, orderAscending );
    }

    public String getId()
    {
        return "configurable";
    }

    public List<User> getUsers()
        throws UserManagerException
    {
        return userManagerImpl.getUsers();
    }

    public List<User> getUsers( boolean orderAscending )
        throws UserManagerException
    {
        return userManagerImpl.getUsers( orderAscending );
    }

    public boolean isReadOnly()
    {
        return userManagerImpl.isReadOnly();
    }

    public User updateUser( User user )
        throws UserNotFoundException, UserManagerException
    {
        return updateUser( user, false );
    }

    public User updateUser( User user, boolean passwordChangeRequired )
        throws UserNotFoundException, UserManagerException
    {
        return userManagerImpl.updateUser( user, passwordChangeRequired );
    }

    public boolean userExists( String userName )
        throws UserManagerException
    {
        return userManagerImpl.userExists( userName );
    }

    public void setUserManagerImpl( UserManager userManagerImpl )
    {
        this.userManagerImpl = userManagerImpl;
    }

    public String getDescriptionKey()
    {
        return "archiva.redback.usermanager.configurable";
    }
}
