package com.upic.jsoup.sslsupport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.upic.jsoup.sslsupport.InstallCert.SavingTrustManager;

public class SSLSupportInitCert {
	protected static final Logger LOGGER = LoggerFactory.getLogger(SSLSupportInitCert.class);
	private final static String DEFAULT_URL = "certfile/";

	public static String generateSupportFile(String... baseUrl) throws Exception {
		return generateSupportFile(null,0,null,baseUrl);
	}
	
	public static String generateSupportFile(String host, int port, char[] passphrase, String... baseUrl) throws Exception {
		if ((baseUrl.length == 1) || (baseUrl.length == 2)) {
			String[] c = baseUrl[0].split(":");
			host = c[0];
			port = (c.length == 1) ? 443 : Integer.parseInt(c[1]);
			String p = (baseUrl.length == 1) ? "changeit" : baseUrl[1];
			passphrase = p.toCharArray();
		} else {
			LOGGER.info("Usage: java InstallCert <host>[:port] [passphrase]");
			return null;
		}
		
		File f=new File(DEFAULT_URL);
		if(!f.exists()) {
			f.mkdirs();
			
		}
		//文件存在
		File file = new File(DEFAULT_URL+baseUrl[0]);
		if(file.exists()) {
			return DEFAULT_URL+baseUrl[0];
		}
		if (file.isFile() == false) {
			char SEP = File.separatorChar;
			File dir = new File(System.getProperty("java.home") + SEP + "lib" + SEP + "security");
			file = new File(dir, "jssecacerts");
			if (file.isFile() == false) {
				file = new File(dir, "cacerts");
			}
		}
		LOGGER.info("Loading KeyStore " + file + "...");
		InputStream in = new FileInputStream(file);
		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
		ks.load(in, passphrase);
		in.close();

		SSLContext context = SSLContext.getInstance("TLS");
		TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		tmf.init(ks);
		X509TrustManager defaultTrustManager = (X509TrustManager) tmf.getTrustManagers()[0];
		SavingTrustManager tm = new SavingTrustManager(defaultTrustManager);
		context.init(null, new TrustManager[] { tm }, null);
		SSLSocketFactory factory = context.getSocketFactory();

		LOGGER.info("Opening connection to " + host + ":" + port + "...");
		SSLSocket socket = (SSLSocket) factory.createSocket(host, port);
		socket.setSoTimeout(10000);
		try {
			LOGGER.info("Starting SSL handshake...");
			socket.startHandshake();
			socket.close();
			LOGGER.info("");
			LOGGER.info("No errors, certificate is already trusted");
		} catch (SSLException e) {
			LOGGER.error("SSLException"+e.getMessage());
//			e.printStackTrace(System.out);
		}

		X509Certificate[] chain = tm.chain;
		if (chain == null) {
			LOGGER.info("Could not obtain server certificate chain");
			return null;
		}

//		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		LOGGER.info("");
		LOGGER.info("Server sent " + chain.length + " certificate(s):");
		LOGGER.info("");
		MessageDigest sha1 = MessageDigest.getInstance("SHA1");
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		for (int i = 0; i < chain.length; i++) {
			X509Certificate cert = chain[i];
			LOGGER.info(" " + (i + 1) + " Subject " + cert.getSubjectDN());
			LOGGER.info("   Issuer  " + cert.getIssuerDN());
			sha1.update(cert.getEncoded());
			LOGGER.info("   sha1    " + toHexString(sha1.digest()));
			md5.update(cert.getEncoded());
			LOGGER.info("   md5     " + toHexString(md5.digest()));
			LOGGER.info("");
		}

		LOGGER.info("Enter certificate to add to trusted keystore or 'q' to quit: [1]");
//		String line = reader.readLine().trim();
		int k;
		try {
			//k = (line.length() == 0) ? 0 : Integer.parseInt(line) - 1;
			k=0;
		} catch (NumberFormatException e) {
			LOGGER.info("KeyStore not changed");
			return null;
		}

		X509Certificate cert = chain[k];
		String alias = host + "-" + (k + 1);
		ks.setCertificateEntry(alias, cert);

		OutputStream out = new FileOutputStream(DEFAULT_URL+baseUrl[0]);
		ks.store(out, passphrase);
		out.close();

//		LOGGER.info("");
		LOGGER.info(cert.toString());
//		LOGGER.info("");
		LOGGER.info("Added certificate to keystore 'jssecacerts' using alias '" + alias + "'");
		return DEFAULT_URL+baseUrl[0];
	}

	private static void creatFile() {
		
	}
	
	
	
	private static final char[] HEXDIGITS = "0123456789abcdef".toCharArray();

	private static String toHexString(byte[] bytes) {
		StringBuilder sb = new StringBuilder(bytes.length * 3);
		for (int b : bytes) {
			b &= 0xff;
			sb.append(HEXDIGITS[b >> 4]);
			sb.append(HEXDIGITS[b & 15]);
			sb.append(' ');
		}
		return sb.toString();
	}

	private static class SavingTrustManager implements X509TrustManager {

		private final X509TrustManager tm;
		private X509Certificate[] chain;

		SavingTrustManager(X509TrustManager tm) {
			this.tm = tm;
		}

		public X509Certificate[] getAcceptedIssuers() {
			throw new UnsupportedOperationException();
		}

		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			throw new UnsupportedOperationException();
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			this.chain = chain;
			tm.checkServerTrusted(chain, authType);
		}
	}
}
