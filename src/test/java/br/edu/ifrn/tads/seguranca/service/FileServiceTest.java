package br.edu.ifrn.tads.seguranca.service;

import org.testng.annotations.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static org.testng.Assert.*;

public class FileServiceTest
{
	@Test
	public void testHash() throws IOException, NoSuchAlgorithmException
	{
		FileService fileService = new FileService();
		assertEquals(
			fileService.hashStream(this.getClass().getClassLoader().getResourceAsStream("fileToHash")),
			"827ccb0eea8a706c4c34a16891f84e7b"
		);
	}
}