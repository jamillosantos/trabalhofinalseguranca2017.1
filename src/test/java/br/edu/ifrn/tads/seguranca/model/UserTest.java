package br.edu.ifrn.tads.seguranca.model;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class UserTest
{
	@Test
	public void testGeneratePassword() throws Exception
	{
		User user = new User(null, null, "12345");
		assertNotEquals("12345", user.getPassword());
	}

	@Test
	public void testCheckPassword() throws Exception
	{
		User user = new User(null, null, "12345");
		assertTrue(user.checkPassword("12345"));
		assertFalse(user.checkPassword("1234"));
	}

	@Test
	public void testSetPassword() throws Exception
	{
		User user = new User(null, null, "12345");
		String salt = user.getSalt();
		String password = user.getPassword();

		user.setPassword("12345");
		assertNotEquals(user.getPassword(), password);
		assertNotEquals(user.getSalt(), salt);
	}

}