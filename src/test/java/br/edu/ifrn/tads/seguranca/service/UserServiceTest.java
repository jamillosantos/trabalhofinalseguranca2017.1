package br.edu.ifrn.tads.seguranca.service;

import br.edu.ifrn.tads.seguranca.TrabalhoFinalApplication;
import br.edu.ifrn.tads.seguranca.model.Group;
import br.edu.ifrn.tads.seguranca.model.Permission;
import br.edu.ifrn.tads.seguranca.model.User;
import org.junit.Assert;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

@SpringApplicationConfiguration(classes = TrabalhoFinalApplication.class)
@WebAppConfiguration
@Test(groups = "users")
public class UserServiceTest extends AbstractTestNGSpringContextTests
{
	@Inject
	private UserService _service;

	@Inject
	private GroupService _groupService;

	@Test
	@Transactional
	public void testInsertion() throws Exception
	{
		long count = this._service.count();
		User user = new User("GI Joe", "john@doe.com", "12345");
		user.getGroups().add(Group.builder().name("Group 1").build());
		this._service.save(user);
		assertEquals(this._service.count(), count + 1);
		assertNotNull(user.getId());
	}

	@Test
	public void testRemoval() throws Exception
	{
		long count = this._service.count();
		User user = new User("GI Joe", "john@doe.com", "12345");
		this._service.save(user);
		this._service.delete(user);
		assertEquals(this._service.count(), count);
	}

	@Test
	public void testFindAll() throws Exception
	{
		long count = this._service.count();
		this._service.save(new User("GI Joe 1", "john1@doe.com", "cobra1"));
		this._service.save(new User("GI Joe 2", "john2@doe.com", "cobra2"));
		this._service.save(new User("GI Joe 3", "john3@doe.com", "cobra3"));
		List<User> list = new ArrayList<>();
		this._service.findAll().forEach(list::add);
		assertEquals(list.size(), 3 + count);
	}

	@Test
	public void testFindByEmailAndPassword() throws Exception
	{
		this._service.save(
			new User("GI Joe", "john@doe.com", "12345")
		);

		User user = this._service.findByEmailAndPassword("john@doe.com", "12345");
		Assert.assertNotNull(user);
	}

	@Test
	public void testFindByEmailAndPasswordShouldFail() throws Exception
	{
		this._service.save(
			new User("GI Joe", "john@doe.com", "12345")
		);

		User user = this._service.findByEmailAndPassword("john@doe.com", "cobra");
		Assert.assertNull(user);
	}

	@Test
	public void testFindByEmailAndPasswordUserNotFound() throws Exception
	{
		User user = this._service.findByEmailAndPassword("jose@doe.com", "cobra");
		Assert.assertNull(user);
	}
}