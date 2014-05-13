/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intel.mtwilson.tag.client.jaxrs;

import com.intel.dcsg.cpg.io.UUID;
import com.intel.mtwilson.client.jaxrs.common.MtWilsonClient;
import com.intel.mtwilson.tag.model.TpmPassword;
import java.net.URL;
import java.util.Properties;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 *
 * @author ssbangal
 */
public class RevokeTagCertificate extends MtWilsonClient {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Selections.class);

    public RevokeTagCertificate(URL url) throws Exception{
        super(url);
    }

    public RevokeTagCertificate(Properties properties) throws Exception {
        super(properties);
    }    

        
    /**
     * This function revokes the specified certificate and sends the revocation information to Mt.Wilson. 
     * @param UUID of the certificate that needs to be revoked.
     * @since Mt.Wilson 2.0
     * @mtwRequiresPermissions tag_certificates:delete
     * @mtwContentTypeReturned JSON/XML/YAML
     * @mtwMethodType POST
     * @mtwSampleRestCall
     * <pre>
     * https://server.com:8181/mtwilson/v2/rpc/revoke-tag-certificate
     * Intput: {"certificate_id":"a6544ff4-6dc7-4c74-82be-578592e7e3ba"}
     * </pre>
     * @mtwSampleApiCall
     * <pre>
     *  RevokeTagCertificate client = new RevokeTagCertificate(My.configuration().getClientProperties());
     *  client.revokeTagCertificate("a6544ff4-6dc7-4c74-82be-578592e7e3ba");
     * </pre>
     */
    public void revokeTagCertificate(UUID certificateId) {
        log.debug("target: {}", getTarget().getUri().toString());
        Response obj = getTarget().path("rpc/revoke-tag-certificate").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(certificateId));
    }
        
}