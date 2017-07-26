package br.edu.ifrn.tads.seguranca.service;

import br.edu.ifrn.tads.seguranca.model.User;
import sun.security.tools.keytool.CertAndKeyGen;
import sun.security.x509.X500Name;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.stream.Stream;

@Named
public class CertService
{
	private static final int KEYSIZE = 1024;
	private static final String ORGANIZATION_UNIT = "IFRN";
	private static final String ORGANIZATION = "IFRN";
	private static final String CITY = "Natal";
	private static final String STATE = "RN";
	private static final String COUNTRY = "BR";
	private static final long VALIDITY = 365; // 3 years
	private static final String ALIAS = "trabseg";
	private static final char[] keyPass = "JP829YEDHWEK7GHW87TG2O7JGHDSO".toCharArray();

	@Inject
	private UserService userService;

	public PrivateKey generate(User user) throws Exception
	{
		CertAndKeyGen keypair = new CertAndKeyGen("RSA", "SHA1WithRSA", null);

		X500Name x500Name = new X500Name(user.getName(), ORGANIZATION_UNIT, ORGANIZATION, CITY, STATE, COUNTRY);
		keypair.generate(KEYSIZE);

		PrivateKey privateKey = keypair.getPrivateKey();
		PublicKey publicKey = keypair.getPublicKey();

		user.setPublicKey(publicKey);
		this.userService.save(user);
		return privateKey;
	}

	public PrivateKey privateKeyFromStream(InputStream stream)
	{
		try
		{
			stream.skip(28); // PEM header
			byte[] fileContent = new byte[stream.available() - 25 /* PEM footer */];
			stream.read(fileContent);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(fileContent));
			KeyFactory fact = KeyFactory.getInstance("RSA");
			PrivateKey priv = fact.generatePrivate(keySpec);
			// It ensures the private key is not laying around the memory where is not needed
			Arrays.fill(fileContent, (byte) 0);
			return priv;
		}
		catch (Exception e)
		{
			return null;
		}
	}

	public PublicKey publicKeyFromStream(InputStream stream)
	{
		try
		{
			byte[] fileContent = new byte[stream.available()];
			stream.read(fileContent);
			byte[] data = Base64.getDecoder().decode(fileContent);
			X509EncodedKeySpec spec = new X509EncodedKeySpec(data);
			KeyFactory fact = KeyFactory.getInstance("RSA");
			return fact.generatePublic(spec);
		}
		catch (Exception e)
		{
			return null;
		}
	}

	public boolean publicKeyToStream(PublicKey key, OutputStream stream)
	{
		try
		{
			KeyFactory fact = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec spec = fact.getKeySpec(key,
				X509EncodedKeySpec.class);
			stream.write(Base64.getEncoder().encode(spec.getEncoded()));
			return true;
		}
		catch (IOException | InvalidKeySpecException | NoSuchAlgorithmException e)
		{
			return false;
		}
	}

	public boolean privateKeyToStream(PrivateKey key, OutputStream stream)
	{
		try
		{
			KeyFactory fact = KeyFactory.getInstance("RSA");
			PKCS8EncodedKeySpec spec = fact.getKeySpec(key,
				PKCS8EncodedKeySpec.class);
			stream.write("-----BEGIN PRIVATE KEY-----\n".getBytes());
			stream.write(Base64.getEncoder().encode(spec.getEncoded()));
			stream.write("\n-----END PRIVATE KEY-----".getBytes());
			return true;
		}
		catch (InvalidKeySpecException | IOException | NoSuchAlgorithmException e)
		{
			return false;
		}
	}

	/*
	public String generateSignature(User user, InputStream stream)
	{
		Signature signature = null;
		try
		{
			signature = user.getPublicKey().getSig;

			byte[] buffer = new byte[1024];
			int len;
			while ((len = stream.read(buffer)) >= 0)
				signature.update(buffer, 0, len);
			return Base64.getEncoder().encodeToString(signature.sign());
		}
		catch (SignatureException | NoSuchAlgorithmException | NoSuchProviderException | IOException e)
		{
			return null;
		}
	}
	*/

	public boolean checkSignature(User user, String signature, InputStream stream)
	{
		return this.checkSignature(user.getPublicKey(), signature, stream);
	}

	protected boolean checkSignature(PublicKey key, String signature, InputStream stream)
	{
		try
		{
			byte[] sigbytes = Base64.getDecoder().decode(signature);
			Signature sig = Signature.getInstance("SHA256withRSA");
			sig.initVerify(key);
			byte[] buffer = new byte[1024];
			int len;
			while (stream.available() > 0)
			{
				len = stream.read(buffer);
				sig.update(buffer, 0, len);
			}
			return sig.verify(sigbytes);
		}
		catch (IOException | SignatureException | InvalidKeyException | NoSuchAlgorithmException e)
		{
			return false;
		}
	}
}
