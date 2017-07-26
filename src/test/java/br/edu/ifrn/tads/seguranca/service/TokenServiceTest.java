package br.edu.ifrn.tads.seguranca.service;

import br.edu.ifrn.tads.seguranca.TrabalhoFinalApplication;
import br.edu.ifrn.tads.seguranca.model.Token;
import br.edu.ifrn.tads.seguranca.model.User;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

@SpringApplicationConfiguration(classes = TrabalhoFinalApplication.class)
@WebAppConfiguration
@Test(groups = "tokens")
public class TokenServiceTest extends AbstractTestNGSpringContextTests
{
	@Inject
	private TokenService _service;

	@Inject
	private UserService _userService;

	@Test
	public void testInsertion() throws Exception
	{
		long count = this._service.count();

		User user = new User("GI Joe", "john@doe.com", "12345");
		this._userService.save(user);
		this._service.save(new Token(user, "127.0.0.1", null, "Netscape 1.0"));
		assertEquals(this._service.count(), count + 1);
	}

	@Test
	public void testRemoval() throws Exception
	{
		long count = this._service.count();
		User user = new User("GI Joe", "john@doe.com", "12345");
		this._userService.save(user);
		Token token = new Token(user, "127.0.0.1", null, "Netscape 1.0");
		this._service.save(token);
		assertEquals(this._service.count(), count + 1);
		this._service.delete(token);
		assertEquals(this._service.count(), count);
	}

	@Test
	public void testCount() throws Exception
	{
		long count = this._service.count();
		User user = new User("GI Joe", "john@doe.com", "12345");
		this._userService.save(user);
		this._service.save(new Token(user, "127.0.0.1", null, "Netscape 1.0"));
		this._service.save(new Token(user, "127.0.0.1", null, "Netscape 1.0"));
		this._service.save(new Token(user, "127.0.0.1", null, "Netscape 1.0"));
		assertEquals(this._service.count(), 3 + count);
	}

	@Test
	public void testFindAll() throws Exception
	{
		long count = this._service.count();
		User user = new User("GI Joe", "john@doe.com", "12345");
		this._userService.save(user);
		this._service.save(new Token(user, "127.0.0.1", null, "Netscape 1.0"));
		this._service.save(new Token(user, "127.0.0.1", null, "Netscape 1.0"));
		this._service.save(new Token(user, "127.0.0.1", null, "Netscape 1.0"));
		List<Token> list = new ArrayList<>();
		this._service.findAll().forEach(list::add);
		assertEquals(list.size(), 3 + count);
	}

	@Test
	public void testFindByUserAndToken() throws Exception
	{
		long count = this._service.count();
		User user = new User("GI Joe", "john@doe.com", "12345");
		this._userService.save(user);
		Token token = new Token(user, "127.0.0.1", null, "Netscape 1.0");
		String t = token.getToken();
		this._service.save(token);

		Token found = this._service.findByToken(user, t);
		assertNotNull(found);
		assertEquals(found.getToken(), t);
	}
}