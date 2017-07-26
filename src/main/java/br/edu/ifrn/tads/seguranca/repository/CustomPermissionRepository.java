package br.edu.ifrn.tads.seguranca.repository;

import br.edu.ifrn.tads.seguranca.model.Permission;
import br.edu.ifrn.tads.seguranca.model.User;

public interface CustomPermissionRepository
{
	boolean isAuthorized(User user, Permission permission);
}
