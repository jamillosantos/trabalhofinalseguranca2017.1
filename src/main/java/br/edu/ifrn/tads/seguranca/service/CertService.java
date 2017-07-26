package br.edu.ifrn.tads.seguranca.service;

import br.edu.ifrn.tads.seguranca.model.User;
import sun.security.tools.keytool.CertAndKeyGen;
import sun.security.x509.X500Name;

import javax.inject.Inject;
import javax.inject.Named;
import java.security.*;

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
}
