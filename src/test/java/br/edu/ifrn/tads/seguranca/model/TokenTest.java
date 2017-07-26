package br.edu.ifrn.tads.seguranca.model;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class TokenTest
{
	@Test
	public void testGenerateToken()
	{
		Token token = new Token(
			new User("GI Joe", "john@doe.com", "12345"),
			"127.0.0.1",
			"127.0.0.1",
			"Netscape 1.0"
		);
		assertNotNull(token.getToken());
		assertNotNull(token.getSalt());
	}

	@Test
	public void testCheck()
	{
		Token token = new Token(
			new User("GI Joe", "john@doe.com", "12345"),
			"127.0.0.1",
			"127.0.0.1",
			"Netscape 1.0"
		);
		assertTrue(token.check("127.0.0.1", "127.0.0.1", "Netscape 1.0"));
		assertFalse(token.check("127.0.0.1", "127.0.0.1", "netscape 1.0"));
		assertFalse(token.check("127.0.0.1", null, "netscape 1.0"));
	}
}