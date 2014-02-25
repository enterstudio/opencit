/*
 * Copyright (C) 2014 Intel Corporation
 * All rights reserved.
 */
package com.intel.mtwilson.client.jaxrs;

import com.intel.mtwilson.client.jaxrs.Files;
import com.intel.dcsg.cpg.io.UUID;
import com.intel.mtwilson.My;
import com.intel.mtwilson.as.rest.v2.model.File;
import com.intel.mtwilson.as.rest.v2.model.FileCollection;
import java.util.Properties;
import org.junit.BeforeClass;
import org.junit.Test;
import com.intel.mtwilson.as.rest.v2.model.FileFilterCriteria;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.beanutils.PropertyUtils;

/**
 *
 * @author jbuhacoff
 */
public class FileTest {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(FileTest.class);

    private static Files client = null;
    
    @BeforeClass
    public static void init() throws Exception {
        /*
        String username = "myusername"; // you choose a username
        String password = "changeit"; // you choose a password
        Properties properties = new Properties();
        properties.setProperty("mtwilson.api.baseurl", "http://localhost:8080/v2");
        properties.setProperty("mtwilson.api.keystore", System.getProperty("user.home", ".")+java.io.File.separator+username+".jks");
        properties.setProperty("mtwilson.api.keystore.password", password);
        properties.setProperty("mtwilson.api.key.alias", username);
        properties.setProperty("mtwilson.api.key.password", password);
        client = new Files(properties);
        */
        client = new Files(My.configuration().getClientProperties());
    }
    
    @Test
    public void testSearchCollection() {
        FileFilterCriteria criteria = new FileFilterCriteria();
        criteria.nameContains = "test";
        FileCollection files = client.searchFiles(criteria);
        for(File file : files.getFiles()) {
            log.debug("File name {} id {} content-type {}", file.getName(), file.getId(), file.getContentType());
        }
    }
    @Test
    public void testRetrieveFile() {
        File file = client.retrieveFile((new UUID()).toString());
        log.debug("File name {} id {} content-type {}", file.getName(), file.getId(), file.getContentType());
    }
    

    
    @Test
    public void testAutomaticQueryParamters() throws Exception {
        FileFilterCriteria criteria = new FileFilterCriteria();
        criteria.nameContains = "foo";
        Map<String,Object> properties = ReflectionUtil.getQueryParams(criteria);
                for(Map.Entry<String,Object> queryParam : properties.entrySet()) {
            log.debug("queryParam {} = {}", queryParam.getKey(), queryParam.getValue());
//            target.queryParam(attr.getKey(), queryParam.getValue());
        }

    }
}
