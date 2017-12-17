/*
 * Network.java
 *
 * Created on __DATE__, __TIME__
 */

package com.mona.key;

import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.StringTokenizer;

public class Network {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	X509V1Certificate certificate;
	X509Certificate cert;
	PublicKey pubKey;
	byte[] signature;
	String key;

	/** Creates new form Network */
	public Network() {
		// initComponents();
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		certificate = new X509V1Certificate();
		cert = certificate.getCertificate();
		pubKey = certificate.getPublickey();
		try {
			String[] s = pubKey.toString().split("modulus:");
			String[] s1 = s[1].split("public");
			key = s1[0].trim();
		} catch (Exception e) {
			System.out.println(e);
		}
		signature = certificate.getSign();

	}

	public static void main(String args[]) {
		new Network();
	}

	public String getKey() {
		return key;
	}

}