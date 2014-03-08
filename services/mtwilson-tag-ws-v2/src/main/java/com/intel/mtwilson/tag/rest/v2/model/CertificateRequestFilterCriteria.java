/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intel.mtwilson.tag.rest.v2.model;

import com.intel.dcsg.cpg.io.UUID;
import com.intel.mtwilson.jersey.FilterCriteria;
import javax.ws.rs.QueryParam;

/**
 *
 * @author ssbangal
 */
public class CertificateRequestFilterCriteria implements FilterCriteria<CertificateRequest>{

    @QueryParam("id")
    public UUID id;
    @QueryParam("subjectEqualTo")
    public String subjectEqualTo;
    @QueryParam("certificateIdEqualTo")
    public UUID certificateIdEqualTo;
    @QueryParam("selectionIdEqualTo")
    public UUID selectionIdEqualTo;
    
}