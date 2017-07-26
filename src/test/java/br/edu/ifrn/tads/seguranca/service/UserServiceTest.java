package br.edu.ifrn.tads.seguranca.service;

import br.edu.ifrn.tads.seguranca.TrabalhoFinalApplication;
import br.edu.ifrn.tads.seguranca.model.User;
import org.junit.Assert;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.Test;

import javax.inject.Inject;

import static org.testng.Assert.*;

@SpringApplicationConfiguration(classes = TrabalhoFinalApplication.class)
@WebAppConfiguration
@Test(groups = "users")
public class UserServiceTest extends AbstractTestNGSpringContextTests
{
	@Inject
	private UserService _service;

	@Test
	public void testInsertion() throws Exception
	{
		long count = this._service.count();
		User user = new User("GI Joe", "john@doe.com", "cobra");
		this._service.save(user);
		assertEquals(this._service.count(), count + 1);
		assertNotNull(user.getId());
	}

	@Test
	public void testRemoval() throws Exception
	{
		long count = this._service.count();
		User user = new User("GI Joe", "john@doe.com", "cobra");
		this._service.save(user);
		this._service.delete(user);
		assertEquals(this._service.count(), count);
	}

	@Test
	public void testFindAll() throws Exception
	{
		this._service.save(new User("GI Joe 1", "john1@doe.com", "cobra1"));
		this._service.save(new User("GI Joe 2", "john2@doe.com", "cobra2"));
		this._service.save(new User("GI Joe 3", "john3@doe.com", "cobra3"));
		assertEquals(this._service.count(), 3);
	}

	@Test
	public void testFindByEmailAndPassword() throws Exception
	{
		this._service.save(
			new User("GI Joe", "john@doe.com", "cobra")
		);

		User user = this._service.findByEmailAndPassword("john@doe.com", "cobra");
		Assert.assertNotNull(user);
	}

	@Test
	public void testFindByEmailAndPasswordShouldFail() throws Exception
	{
		this._service.save(
			new User("GI Joe", "john@doe.com", "cobra")
		);

		User user = this._service.findByEmailAndPassword("john@doe.com", "cobra2");
		Assert.assertNull(user);
	}
}