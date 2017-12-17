package com.mona.encryption;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class CipherExample {

	public static void main(String[] args) {
		try {

		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public String encrypt(String key, String filepath) throws Exception {

		String path = new File(".").getAbsolutePath();
		boolean b = new File(path + "\\enc").mkdir();
		File temp = new File("enc");
		File encfile = new File(temp.getAbsolutePath() + "\\"
				+ new File(filepath).getName());
		FileInputStream fis = new FileInputStream(filepath);
		FileOutputStream fos = new FileOutputStream(encfile);
		encryptOrDecrypt(key, Cipher.ENCRYPT_MODE, fis, fos);
		return encfile.toString();
	}

	public String decrypt(String key, String filename) throws Exception {

		String path = new File(".").getAbsolutePath();
		boolean b = new File(path + "\\upload").mkdir();
		String file = new File("upload") + "\\" + new File(filename).getName();

		FileInputStream fis2 = new FileInputStream(filename);
		FileOutputStream fos2 = new FileOutputStream(file);
		encryptOrDecrypt(key, Cipher.DECRYPT_MODE, fis2, fos2);
		return file;
	}

	public void encryptOrDecrypt(String key, int mode, InputStream is,
			OutputStream os) throws Exception {

		DESKeySpec dks = new DESKeySpec(key.getBytes());
		SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
		SecretKey desKey = skf.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DES"); // DES/ECB/PKCS5Padding for
		// SunJCE

		if (mode == Cipher.ENCRYPT_MODE) {
			cipher.init(Cipher.ENCRYPT_MODE, desKey);
			CipherInputStream cis = new CipherInputStream(is, cipher);
			doCopy(cis, os);
		} else if (mode == Cipher.DECRYPT_MODE) {
			cipher.init(Cipher.DECRYPT_MODE, desKey);
			CipherOutputStream cos = new CipherOutputStream(os, cipher);
			doCopy(is, cos);
		}
	}

	public static void doCopy(InputStream is, OutputStream os)
			throws IOException {
		byte[] bytes = new byte[64];
		int numBytes;
		while ((numBytes = is.read(bytes)) != -1) {
			os.write(bytes, 0, numBytes);
		}
		os.flush();
		os.close();
		is.close();
	}

}