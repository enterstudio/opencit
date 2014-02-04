/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intel.mtwilson.as.rest.v2.model;

import com.intel.dcsg.cpg.io.UUID;
import com.intel.mtwilson.jersey.FilterCriteria;
import javax.ws.rs.QueryParam;

/**
 *
 * @author ssbangal
 */
public class HostAttestationFilterCriteria implements FilterCriteria<HostAttestation> {
    
    @QueryParam("id")
    public UUID id;
    @QueryParam("hostUuid")
    public UUID hostUuid;
    @QueryParam("nameEqualTo")
    public String nameEqualTo;
    @QueryParam("aik")
    public String aik;
    
}
