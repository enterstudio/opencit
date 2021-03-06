/*
 * Copyright (C) 2012 Intel Corporation
 * All rights reserved.
 */
package com.intel.mtwilson.client.cmd;

import com.intel.mtwilson.client.AbstractCommand;
import com.intel.dcsg.cpg.io.Filename;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *     HostTrustResponse getHostTrust(Hostname hostname) throws IOException, ApiException, SignatureException;
 * 
 * @author jbuhacoff
 */
public class DecodeUsername extends AbstractCommand {

    @Override
    public void execute(String[] args) throws Exception {
        if( args.length < 1 ) {
            throw new IllegalArgumentException("Usage: DecodeUsername alias | DecodeUsername -  (and supply username on stdin)");
        }
        if( args[0].equals("-") ) {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            System.out.println(Filename.decode(in.readLine()));
            in.close();
        }
        else {
            System.out.println(Filename.decode(args[0])); 
        }
    }

}
