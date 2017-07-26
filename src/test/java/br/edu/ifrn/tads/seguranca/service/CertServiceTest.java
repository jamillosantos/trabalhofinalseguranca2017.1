package br.edu.ifrn.tads.seguranca.service;

import br.edu.ifrn.tads.seguranca.TrabalhoFinalApplication;
import br.edu.ifrn.tads.seguranca.model.User;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.PrivateKey;
import java.security.PublicKey;

import static org.testng.Assert.*;

@SpringApplicationConfiguration(classes = TrabalhoFinalApplication.class)
@WebAppConfiguration
@Test(groups = "permissions")
public class CertServiceTest extends AbstractTestNGSpringContextTests
{
	@Inject
	private CertService _service;

	@Inject
	private UserService _userService;

	@Test
	public void testGenerate() throws Exception
	{
		User user = new User("GI Joe", "john@doe.com", "12345");
		this._userService.save(user);
		PrivateKey privateKey = this._service.generate(user);
		PublicKey publicKey = user.getPublicKey();
		assertNotNull(publicKey);
		assertNotNull(privateKey);

		//
		// this._service.publicKeyToStream(publicKey, new FileOutputStream("/home/jsantos/public.key"));
		// this._service.privateKeyToStream(privateKey, new FileOutputStream("/home/jsantos/private.pem"));
	}

	@Test
	public void testCheckSignature()
	{
		PublicKey publicKey = this._service.publicKeyFromStream(this.getClass().getClassLoader().getResourceAsStream("public.key"));
		InputStream fs = this.getClass().getClassLoader().getResourceAsStream("fileToHash");
		assertTrue(this._service.checkSignature(
			publicKey,
			"NXO7dix7CUGmBkzpkkH3lC33Ch0FUy3v887cv1Fp+YGZzARwIEanHn3+x5LU7i0t1exqGk5dWj9+" +
				"GlJwNnpTocfTP0PVsv1sm6SL7Hsfh0hjRsQAcMa2JpRdY1AhYSD1KPi7lgLwMmn7UoQ4klJVdSDZ" +
				"9ybgKw9Btg5JNMV+kCM=",
			fs
		));
	}

	@Test
	public void testCheckSignatureFail()
	{
		PublicKey publicKey = this._service.publicKeyFromStream(this.getClass().getClassLoader().getResourceAsStream("public.key"));
		InputStream fs = this.getClass().getClassLoader().getResourceAsStream("fileToHash2");
		assertFalse(this._service.checkSignature(
			publicKey,
			"NXO7dix7CUGmBkzpkkH3lC33Ch0FUy3v887cv1Fp+YGZzARwIEanHn3+x5LU7i0t1exqGk5dWj9+" +
				"GlJwNnpTocfTP0PVsv1sm6SL7Hsfh0hjRsQAcMa2JpRdY1AhYSD1KPi7lgLwMmn7UoQ4klJVdSDZ" +
				"9ybgKw9Btg5JNMV+kCM=",
			fs
		));
	}
}