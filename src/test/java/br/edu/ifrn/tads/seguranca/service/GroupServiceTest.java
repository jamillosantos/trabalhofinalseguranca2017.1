package br.edu.ifrn.tads.seguranca.service;

import br.edu.ifrn.tads.seguranca.TrabalhoFinalApplication;
import br.edu.ifrn.tads.seguranca.model.Group;
import br.edu.ifrn.tads.seguranca.model.Permission;
import br.edu.ifrn.tads.seguranca.model.User;
import org.hibernate.Hibernate;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

@SpringApplicationConfiguration(classes = TrabalhoFinalApplication.class)
@WebAppConfiguration
@Test(groups = "permissions")
public class GroupServiceTest extends AbstractTestNGSpringContextTests
{
	@Inject
	private GroupService _service;

	@Inject
	private PermissionService _permissionService;

	private Permission permission1;
	private Permission permission2;
	private Permission permission3;

	private User user1;
	private User user2;

	@BeforeClass
	public void setUp() throws Exception
	{
		this.permission1 = Permission.builder().name("P1").build();
		this._permissionService.save(this.permission1);
		this.permission2 = Permission.builder().name("P2").build();
		this._permissionService.save(this.permission2);
		this.permission3 = Permission.builder().name("P3").build();
		this._permissionService.save(this.permission3);
	}

	@Test
	public void testInsertion() throws Exception
	{
		long count = this._service.count();
		Group group = Group.builder()
			.name("Group 1")
			.permission(this.permission1)
			.permission(this.permission3)
			.build();
		this._service.save(group);
		assertEquals(this._service.count(), count + 1);
		assertNotNull(group.getId());

		Group group2 = this._service.findOne(group.getId());
		assertEquals(group.getId(), group2.getId());
		assertEquals(group.getName(), group2.getName());
	}

	@Test
	public void testRemoval() throws Exception
	{
		long count = this._service.count();
		Group group = Group.builder()
			.name("Group 1")
			.permission(this.permission1)
			.permission(this.permission3)
			.build();
		this._service.save(group);
		this._service.delete(group);
		assertEquals(this._service.count(), count);
	}

	@Test
	public void testCount() throws Exception
	{
		long count = this._service.count();
		this._service.save(Group.builder()
			.name("Group 1")
			.permission(this.permission1)
			.permission(this.permission3)
			.build());
		this._service.save(Group.builder()
			.name("Group 2")
			.permission(this.permission3)
			.build());
		this._service.save(Group.builder()
			.name("Group 3")
			.permission(this.permission2)
			.permission(this.permission3)
			.build());
		assertEquals(this._service.count(), 3 + count);
	}

	@Test
	public void testFindAll() throws Exception
	{
		long count = this._service.count();
		this._service.save(Group.builder()
			.name("Group 1")
			.permission(this.permission1)
			.permission(this.permission3)
			.build());
		this._service.save(Group.builder()
			.name("Group 2")
			.permission(this.permission3)
			.build());
		this._service.save(Group.builder()
			.name("Group 3")
			.permission(this.permission2)
			.permission(this.permission3)
			.build());
		List<Group> list = new ArrayList<>();
		this._service.findAll().forEach(list::add);
		assertEquals(list.size(), 3 + count);
	}
}