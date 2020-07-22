package io.fis.mx.interceptor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Properties;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Signer {
	
	private static Signer instance = new Signer();
	private Properties prop = new Properties();
	
	private Logger logger = LoggerFactory.getLogger(Signer.class.getName());
	private PrivateKey privateKey;
	private PublicKey publicKey;
	public static Signer getInstance() {
		return instance;
	}
	private Signer() {
		InputStream input = null;
		try {
			input = new FileInputStream(new File(Signer.class.getClassLoader().getResource("config.properties").getFile()));
			prop.load(input);
			privateKey = readPrivateKeyFromKeystore();
			publicKey = readPublicCDC();
		} catch (Exception e) {
			logger.error("Configuration file not found [config.properties].");
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					logger.error("Error reading configuration file [config.properties].");
				}
			}
		}
	}
	public String signPayload(String payload) {
		String signature = null;
		try {
			Signature signing = Signature.getInstance("SHA256withECDSA");
			signing.initSign(privateKey);
			signing.update(payload.getBytes());
			signature = Hex.encodeHexString(signing.sign(), true);
		} catch (NoSuchAlgorithmException e) {
			logger.error("Signing algorithm invalid.");
		} catch (InvalidKeyException e) {
			logger.error("Invalid Private key.");
		} catch (SignatureException e) {
			logger.error("Error to signing payload.");
		}
		return signature;
	}
	
	public Boolean verifyPayload(String payload, String signature) {
		
		Signature sign = null;
		Boolean isVerify = false;
		byte[] signatureBytes = null;
		if (publicKey != null) {
			try {
				signatureBytes = Hex.decodeHex(signature);
				sign = Signature.getInstance("SHA256withECDSA");
				sign.initVerify(publicKey);
				sign.update(payload.getBytes());
				isVerify = sign.verify(signatureBytes);
			} catch (NoSuchAlgorithmException e) {
				logger.error("Signature algorithm invalid.");
			} catch (InvalidKeyException e) {
				logger.error("Invalid Public key.");
			} catch (SignatureException e) {
				logger.error("Signature error to verifying the payload.");
			} catch (DecoderException e) {
				logger.error("Failure during the decoding process to verify the payload.");
			}
		}
		
		return isVerify;
	}
	private PrivateKey readPrivateKeyFromKeystore() {
		PrivateKey ecKey = null;
		try {
			logger.debug("keystore_file:" + prop.getProperty("keystore_file"));
			File file = new File (prop.getProperty("keystore_file"));
			FileInputStream inputStream = new FileInputStream(file);
			KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
			keystore.load(inputStream, prop.getProperty("keystore_password").toCharArray());
			ecKey = (PrivateKey) keystore.getKey(prop.getProperty("key_alias"),prop.getProperty("key_password").toCharArray());
		} catch (KeyStoreException e) {
			logger.error("Invalid keystore.");
		} catch (FileNotFoundException e) {
			logger.error("Keystore file not found [keystore_file].");
		} catch (NoSuchAlgorithmException e) {
			logger.error("Keystore algorithm invalid.");
		} catch (CertificateException e) {
			logger.error("Private key invalid to process keystore.");
		} catch (IOException e) {
			logger.error("Error reading the keystore file [keystore_file].");
		} catch (UnrecoverableKeyException e) {
			logger.error("The keystore cannot be recovered.");
		} finally {
			if(ecKey == null) {
				logger.error("Could not read the private key, please review your configuration.");
			}
		}
		return ecKey;
	}
	public PublicKey readPublicCDC() {
		PublicKey pubKey = null;
		logger.debug("keystore_file:" + prop.getProperty("cdc_cert_file"));
		File file = new File(prop.getProperty("cdc_cert_file"));
		FileInputStream certificate;
		try {
			certificate = new FileInputStream(file);
			CertificateFactory fact = CertificateFactory.getInstance("X.509");
			X509Certificate x509cert = (X509Certificate) fact.generateCertificate(certificate);
			pubKey = x509cert.getPublicKey();
		} catch (FileNotFoundException e) {
			logger.error("CDC Certificate file not found [cdc_cert_file].");
		} catch (CertificateException e) {
			logger.error("Invalid CDC Certificate.");
		} finally {
			if(pubKey == null) {
				logger.error("Could not read the Public Key, please review your configuration");
			}
		}
		
		return pubKey;
	}
}
