package br.edu.ifrn.tads.seguranca.service;

import javax.inject.Named;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Named
public class FileService
{
	String hashFile(File file) throws IOException, NoSuchAlgorithmException
	{
		return this.hashStream(new FileInputStream(file));
	}

	public String hashStream(InputStream stream) throws IOException, NoSuchAlgorithmException
	{
		MessageDigest md = MessageDigest.getInstance("MD5");

		byte[] buff = new byte[1024 * 10];
		int len;
		while (stream.available() > 0)
		{
			len = stream.read(buff);
			md.update(buff, 0, len);
		}

		byte[] bytes = md.digest();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++)
			sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		return sb.toString();
	}
}
