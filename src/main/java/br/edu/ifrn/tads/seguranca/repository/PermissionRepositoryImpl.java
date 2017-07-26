package br.edu.ifrn.tads.seguranca.repository;

import br.edu.ifrn.tads.seguranca.model.*;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class PermissionRepositoryImpl implements CustomPermissionRepository
{
	private final EntityManager entityManager;

	@Inject
	public PermissionRepositoryImpl(EntityManager entityManager)
	{
		this.entityManager = entityManager;
	}

	@Override
	public boolean isAuthorized(User user, Permission permission)
	{
		Query query = this.entityManager.createNativeQuery(
			"SELECT count(*) FROM users u" +
				" INNER JOIN users_groups ug ON (ug.users_id = u.id)" +
				" INNER JOIN groups_permissions gp ON (gp.groups_id = ug.groups_id)" +
				" WHERE (u.id = ?) AND (gp.permissions_id = ?)"
		);
		query.setParameter(1, user.getId());
		query.setParameter(2, permission.getId());
		return (Integer)query.getSingleResult() > 0;
	}
}
