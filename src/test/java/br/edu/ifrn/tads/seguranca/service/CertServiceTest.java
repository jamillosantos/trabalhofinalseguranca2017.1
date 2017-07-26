package br.edu.ifrn.tads.seguranca.service;

import br.edu.ifrn.tads.seguranca.TrabalhoFinalApplication;
import br.edu.ifrn.tads.seguranca.model.Group;
import br.edu.ifrn.tads.seguranca.model.Permission;
import br.edu.ifrn.tads.seguranca.model.User;
import org.hibernate.validator.internal.util.IgnoreJava6Requirement;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.security.PrivateKey;

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
		PrivateKey key = this._service.generate(user);
		assertNotNull(user.getPublicKey());
	}
}