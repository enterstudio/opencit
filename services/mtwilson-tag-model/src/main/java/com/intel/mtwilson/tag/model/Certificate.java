/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intel.mtwilson.tag.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.intel.dcsg.cpg.crypto.Sha1Digest;
import com.intel.dcsg.cpg.crypto.Sha256Digest;
import com.intel.dcsg.cpg.x509.X509Util;
import com.intel.mountwilson.as.common.ASException;
import com.intel.mtwilson.datatypes.ErrorCode;
import com.intel.mtwilson.jersey.CertificateDocument;
import java.io.UnsupportedEncodingException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ssbangal
 */
@JacksonXmlRootElement(localName="certificate")
public class Certificate extends CertificateDocument{
    
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Certificate.class);
    
    private byte[] certificate;
    private Sha1Digest sha1; // 20 bytes      SHA1(CERTIFICATE)  the certificate fingerprint
    private Sha256Digest sha256; // 32 bytes      SHA256(CERTIFICATE)  the certificate fingerprint
    private String subject;
    private String issuer;
    private Date notBefore;
    private Date notAfter;
    private boolean revoked = false;

    public byte[] getCertificate() {
        return certificate;
    }

    public void setCertificate(byte[] certificate) {
        this.certificate = certificate;
    }

    public Sha1Digest getSha1() {
        return sha1;
    }

    public void setSha1(Sha1Digest sha1) {
        this.sha1 = sha1;
    }

    public Sha256Digest getSha256() {
        return sha256;
    }

    public void setSha256(Sha256Digest sha256) {
        this.sha256 = sha256;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public Date getNotBefore() {
        return notBefore;
    }

    public void setNotBefore(Date notBefore) {
        this.notBefore = notBefore;
    }

    public Date getNotAfter() {
        return notAfter;
    }

    public void setNotAfter(Date notAfter) {
        this.notAfter = notAfter;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }

    
    // you still need to setUuid() after calling this since it's not included in the serialized form
    @JsonCreator
    public static Certificate valueOf(String text) throws UnsupportedEncodingException {
        byte[] data = Base64.decodeBase64(text);
        return valueOf(data);
    }

    // you still need to setUuid() after calling this since it's not included in the serialized form
    public static Certificate valueOf(byte[] data) throws UnsupportedEncodingException {
        Certificate certificate = new Certificate();
        certificate.setCertificate(data);
        certificate.setSha1(Sha1Digest.digestOf(data)); // throws UnsupportedEncodingException
        certificate.setSha256(Sha256Digest.digestOf(data)); // throws UnsupportedEncodingException
//        certificate.setPcrEventSha256(Sha256Digest.digestOf(certificate.getSha256().toByteArray()));
//        certificate.setPcrEventSha1(Sha1Digest.digestOf(certificate.getSha1().toByteArray()));
        X509AttributeCertificate attrcert = X509AttributeCertificate.valueOf(data);
        certificate.setIssuer(attrcert.getIssuer());
        certificate.setSubject(attrcert.getSubject());
        // XXX TODO need to verify the certificate against known ca's before we really believe these validity dates... assuming that will happen where they matter.
        certificate.setNotBefore(attrcert.getNotBefore());
        certificate.setNotAfter(attrcert.getNotAfter());
        // assuming revoked = false (default value)
        log.debug("valueOf ok");
        return certificate;
    }
    
    @JsonIgnore
    @Override
    public X509Certificate getX509Certificate() {
        if( certificate == null ) { return null; }
        try {
            log.debug("Certificate bytes length {}", certificate.length);
            return X509Util.decodeDerCertificate(certificate);
        }
        catch(CertificateException ce) {
            //throw new IllegalArgumentException("Cannot decode certificate", e); // XXX TODO  for i18n we need to throw MWException here with an appropriate error code
            throw new ASException(ErrorCode.MS_CERTIFICATE_ENCODING_ERROR, ce.getClass().getSimpleName());
        }
    }

    @JsonIgnore
    @Override
    public void setX509Certificate(X509Certificate certificate) {
        if( certificate == null ) {
            this.certificate = null;
            return;
        }
        try {
            this.certificate = certificate.getEncoded();
        }
        catch(CertificateEncodingException ce) {
            log.error("Error decoding certificat.", ce);
            //throw new IllegalArgumentException("Cannot decode certificate", e); // XXX TODO  for i18n we need to throw MWException here with an appropriate error code
            throw new ASException(ErrorCode.MS_CERTIFICATE_ENCODING_ERROR, ce.getClass().getSimpleName());
        }
    }
    
    
}