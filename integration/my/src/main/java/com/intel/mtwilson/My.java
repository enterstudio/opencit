/*
 * Copyright (C) 2013 Intel Corporation
 * All rights reserved.
 */
package com.intel.mtwilson;

import java.io.IOException;
import java.net.MalformedURLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Convenience class for instantiating an API CLIENT for your unit tests.  Relies on MyConfiguration for
 * your local settings.
 * 
 * How to use it in your code:
 * 
 * ApiClient client = My.client();
 * 
 * @author jbuhacoff
 */
public class My {
    private transient static Logger log = LoggerFactory.getLogger(My.class);
    private static MyConfiguration config = null;
    private static ApiClient client = null;
    private static MyPersistenceManager pm = null;
    
    public static MyConfiguration configuration() throws IOException { 
        if( config == null ) {
             config = new MyConfiguration();
        }
        return config; 
    }
    
    public static ApiClient client() throws MalformedURLException, Exception {
        if( client == null ) {
            log.info("Mt Wilson URL: {}", configuration().getMtWilsonURL().toString());
            client = KeystoreUtil.clientForUserInDirectory(
                configuration().getKeystoreDir(), 
                configuration().getKeystoreUsername(),
                configuration().getKeystorePassword(),
                configuration().getMtWilsonURL());
        }
        return client;
    }
    
    public static MyPersistenceManager persistenceManager() throws IOException {
        if( pm == null ) {
            pm = new MyPersistenceManager(configuration().getProperties(
                    "mtwilson.db.host", "mtwilson.db.port", "mtwilson.db.user", 
                    "mtwilson.db.password", "mtwilson.db.schema", "mtwilson.as.dek"));
        }
        return pm;
    }
}