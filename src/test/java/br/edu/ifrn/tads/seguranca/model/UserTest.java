package br.edu.ifrn.tads.seguranca.model;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class UserTest
{
	@Test
	public void testGeneratePassword() throws Exception
	{
		User user = new User(null, null, "fileToHash");
		assertNotEquals("fileToHash", user.getPassword());
	}

	@Test
	public void testCheckPassword() throws Exception
	{
		User user = new User(null, null, "fileToHash");
		assertTrue(user.checkPassword("fileToHash"));
		assertFalse(user.checkPassword("1234"));
	}

	@Test
	public void testSetPassword() throws Exception
	{
		User user = new User(null, null, "fileToHash");
		String salt = user.getSalt();
		String password = user.getPassword();

		user.setPassword("fileToHash");
		assertNotEquals(user.getPassword(), password);
		assertNotEquals(user.getSalt(), salt);
	}

}