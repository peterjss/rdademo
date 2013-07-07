/*
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */


package com.iaspec.rda.plugins.rfid.license;


import com.iaspec.rda.plugins.rfid.entity.Device;
import com.iaspec.rda.plugins.rfid.util.ExceptionMessages;
import com.iaspec.rda.plugins.rfid.util.RdaException;
import com.iaspec.rda.plugins.rfid.util.ResourceHelper;
import com.iaspec.rda.rfid.server.crypto.entity.CertificateDnInfoDTO;
import com.iaspec.rda.rfid.server.crypto.entity.SignatureVerificationResultHolder;
import com.iaspec.rda.rfid.server.crypto.exception.CryptoException;
import com.iaspec.rda.rfid.server.crypto.exception.SignatureInvalidException;
import com.iaspec.rda.rfid.server.crypto.impl.ChallengeVerifier;
import com.iaspec.rda.rfid.server.crypto.util.CertUtil;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;


/**
 * Class description goes here. User: Peter
 *
 * @author <a href="mailto:yang.li@iaspec.com">Peter</a>
 * @version 1.00 13-1-14
 */
public class LicenseReader
{
    private static final Logger logger = LoggerFactory.getLogger(LicenseReader.class);

    public static void verifyChallengeCode(String challenge, String expect, Device device) throws RdaException
    {
        ChallengeVerifier verifier = ChallengeVerifier.getInstance();
        byte[] pkcs7 = Base64.decode(challenge);
        SignatureVerificationResultHolder resultHolder = null;
        try
        {
            resultHolder = verifier.verifySignature(pkcs7);
        }
        catch (SignatureInvalidException se)
        {
            throw new RdaException(ExceptionMessages.EXCEPTION_INVALID_DECRYPTED_CHALLENGE);
        }
        catch (CryptoException se)
        {
            throw new RdaException(ExceptionMessages.EXCEPTION_INVALID_DECRYPTED_CHALLENGE);
        }
        CertificateDnInfoDTO certSubjectDn = CertUtil.getCertificateSubjectInfo(resultHolder.signingCertChain[0]);
        // Handle CN checks
        String cn = certSubjectDn.getCn().get(0).toString();

        if (!cn.equalsIgnoreCase(device.getId()))
        {
            throw new RdaException(ExceptionMessages.EXCEPTION_INVALID_LICENSE);
        }

        logger.debug("Signature Verification success: certSubject=["
                + resultHolder.signingCertChain[0].getSubjectDN().toString() + "], orignialContent=["
                + new String(resultHolder.originalData) + "]");

        if (!new String(resultHolder.originalData).equalsIgnoreCase(expect))
        {
            throw new RdaException(ExceptionMessages.EXCEPTION_INVALID_DECRYPTED_CHALLENGE);
        }

        try
        {
            KeyStore trustedStore = KeyStore.getInstance("JKS");
            trustedStore.load(null, null);
            // byte[] certBytes = IOUtils.toByteArray(new
            // FileInputStream("RDA_RFID_CA_2.cer")); //false CA certificate

            // byte[] certBytes = IOUtils.toByteArray(new
            // FileInputStream("RDA_RFID_CA.cer"));
            byte[] certBytes = IOUtils.toByteArray(ResourceHelper.readResource("RDA_RFID_CA.cer"));

            // valid CA certificate
            X509Certificate cert = CertUtil.getX509Certificate(certBytes);
            // may add any trusted certificate (CA or Self-signed) to the
            // keystore...
            trustedStore.setCertificateEntry(cert.getSubjectDN().getName().toString(), cert);

            verifier.isCertificateTrust(resultHolder.signingCertChain[0], trustedStore, null);

            // if trusted, do CRL verification if crl can supplied
            /*
             * if
			 * (!CertUtil.verifyRevoked(ResourceHelper.readResource("crl.crl"),
			 * cert)) { throw new
			 * RdaException(ExceptionMessages.EXCEPTION_CERTIFICATE_IS_REVOKED);
			 * }
			 */

        }
        catch (com.iaspec.rda.rfid.server.crypto.exception.CertificateNotValidException se)
        {
            throw new RdaException(ExceptionMessages.EXCEPTION_INVALID_LICENSE);
        }
        catch (CertificateException ce)
        {
            throw new RdaException(ExceptionMessages.EXCEPTION_INVALID_DECRYPTED_CHALLENGE);
        }
        catch (RdaException e)
        {
            throw new RdaException(e.getMessage());
        }
        catch (Exception e)
        {
            throw new RdaException(ExceptionMessages.EXCEPTION_SYSTEM);
        }

        logger.debug("The certificate is trusted");
    }
}
