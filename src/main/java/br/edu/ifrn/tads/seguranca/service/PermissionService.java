package br.edu.ifrn.tads.seguranca.service;

import br.edu.ifrn.tads.seguranca.model.Permission;
import br.edu.ifrn.tads.seguranca.model.User;
import br.edu.ifrn.tads.seguranca.repository.PermissionRepository;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class PermissionService extends Service<Permission, Long>
{
	@Inject
	public PermissionService(PermissionRepository repository)
	{
		super(repository);
	}

	public boolean isAuthorized(User user, Permission permission)
	{
		return ((PermissionRepository)this.getRepository()).isAuthorized(user, permission);
	}
}
