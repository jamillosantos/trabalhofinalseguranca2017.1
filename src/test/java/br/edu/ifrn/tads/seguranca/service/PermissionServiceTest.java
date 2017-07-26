package br.edu.ifrn.tads.seguranca.service;

import br.edu.ifrn.tads.seguranca.TrabalhoFinalApplication;
import br.edu.ifrn.tads.seguranca.model.Group;
import br.edu.ifrn.tads.seguranca.model.Permission;
import br.edu.ifrn.tads.seguranca.model.User;
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
@Test(groups = "permissions")
public class PermissionServiceTest extends AbstractTestNGSpringContextTests
{
	@Inject
	private PermissionService _service;

	@Inject
	private GroupService _groupService;

	@Inject
	private UserService _userService;

	@Test
	public void testInsertion() throws Exception
	{
		long count = this._service.count();
		Permission permission = Permission.builder().name("Permission 1").build();
		this._service.save(permission);
		assertEquals(this._service.count(), count + 1);
		assertNotNull(permission.getId());
	}

	@Test
	public void testRemoval() throws Exception
	{
		long count = this._service.count();
		Permission permission = Permission.builder().name("Permission 1").build();
		this._service.save(permission);
		this._service.delete(permission);
		assertEquals(this._service.count(), count);
	}

	@Test
	public void testCount() throws Exception
	{
		long count = this._service.count();
		this._service.save(Permission.builder().name("Permission 1").build());
		this._service.save(Permission.builder().name("Permission 2").build());
		this._service.save(Permission.builder().name("Permission 3").build());
		assertEquals(this._service.count(), 3 + count);
	}

	@Test
	public void testFindAll() throws Exception
	{
		long count = this._service.count();
		this._service.save(Permission.builder().name("Permission 1").build());
		this._service.save(Permission.builder().name("Permission 2").build());
		this._service.save(Permission.builder().name("Permission 3").build());
		List<Permission> list = new ArrayList<>();
		this._service.findAll().forEach(list::add);
		assertEquals(list.size(), 3 + count);
	}

	@Test
	@Transactional
	public void testIsAuthorized() throws Exception
	{
		Permission permission1 = Permission.builder().name("Permission 1").build();
		this._service.save(permission1);
		Permission permission2 = Permission.builder().name("Permission 2").build();
		this._service.save(permission2);
		Permission permission3 = Permission.builder().name("Permission 3").build();
		this._service.save(permission3);

		Group group1 = Group.builder()
			.name("Group 1")
			.permission(permission1)
			.permission(permission2)
			.build();
		this._groupService.save(group1);
		Group group2 = Group.builder()
			.name("Group 2")
			.permission(permission2)
			.build();
		this._groupService.save(group2);
		Group group3 = Group.builder()
			.name("Group 3")
			.permission(permission3)
			.build();
		this._groupService.save(group3);

		User user1 = new User("GI Joe", "john@doe.com", "fileToHash");
		this._userService.save(user1);
		user1.getGroups().add(group1);
		this._userService.save(user1);

		User user2 = new User("Thunderbolts", "thunderbolts@doe.com", "54321");
		this._userService.save(user2);
		user2.getGroups().add(group2);
		this._userService.save(user2);

		User user3 = new User("Cybercops", "cybercops@doe.com", "15243");
		this._userService.save(user3);
		user3.getGroups().add(group3);
		this._userService.save(user3);

		User user4 = new User("Giban", "giban@doe.com", "31542");
		this._userService.save(user4);
		user4.getGroups().add(group1);
		user4.getGroups().add(group3);
		this._userService.save(user4);

		assertTrue(this._service.isAuthorized(user1, permission1));
		assertTrue(this._service.isAuthorized(user1, permission2));
		assertFalse(this._service.isAuthorized(user1, permission3));

		assertFalse(this._service.isAuthorized(user2, permission1));
		assertTrue(this._service.isAuthorized(user2, permission2));
		assertFalse(this._service.isAuthorized(user2, permission3));

		assertFalse(this._service.isAuthorized(user3, permission1));
		assertFalse(this._service.isAuthorized(user3, permission2));
		assertTrue(this._service.isAuthorized(user3, permission3));

		assertTrue(this._service.isAuthorized(user4, permission1));
		assertTrue(this._service.isAuthorized(user4, permission2));
		assertTrue(this._service.isAuthorized(user4, permission3));
	}
}