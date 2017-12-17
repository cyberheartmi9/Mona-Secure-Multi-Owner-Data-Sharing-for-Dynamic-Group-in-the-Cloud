package com.mona.key;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.X509Certificate;
import java.util.Date;

import javax.security.auth.x500.X500Principal;

import org.bouncycastle.x509.X509V1CertificateGenerator;

public class X509V1Certificate {
	public static X509Certificate generateV1Certificate(KeyPair pair)
			throws InvalidKeyException, NoSuchProviderException,
			SignatureException {
		// generate the certificate
		X509V1CertificateGenerator certGen = new X509V1CertificateGenerator();

		certGen.setSerialNumber(BigInteger.valueOf(System.currentTimeMillis()));
		certGen.setIssuerDN(new X500Principal("CN=BSMR"));
		certGen.setNotBefore(new Date(System.currentTimeMillis() - 50000));
		certGen.setNotAfter(new Date(System.currentTimeMillis() + 50000));
		certGen.setSubjectDN(new X500Principal("CN=BSMR"));
		certGen.setPublicKey(pair.getPublic());
		certGen.setSignatureAlgorithm("SHA256WithRSAEncryption");

		return certGen.generateX509Certificate(pair.getPrivate(), "BC");
	}

	public X509Certificate getCertificate() {
		try {
			// create the keys
			KeyPair pair = Utils.generateRSAKeyPair();

			// generate the certificate
			cert = generateV1Certificate(pair);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cert;
	}

	public PublicKey getPublickey() {
		return cert.getPublicKey();
	}

	public byte[] getSign(){
		return cert.getSignature();
	}
	X509Certificate cert;
}
