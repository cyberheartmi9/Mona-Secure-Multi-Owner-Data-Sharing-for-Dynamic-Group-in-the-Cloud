package com.mona.key;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.Security;

/**
 * Chapter 6 Utils
 */
public class Utils
{
    /**
     * Create a random 1024 bit RSA key pair
     */
    public static KeyPair generateRSAKeyPair()
        throws Exception
	{
    	Security
		.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        KeyPairGenerator  kpGen = KeyPairGenerator.getInstance("RSA", "BC");
    
        kpGen.initialize(1024, new SecureRandom());
    
        return kpGen.generateKeyPair();
	}
}