package br.edu.ifrn.tads.seguranca.repository;

import br.edu.ifrn.tads.seguranca.model.QUser;
import br.edu.ifrn.tads.seguranca.model.User;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.persistence.EntityManager;

@Repository
public class UserRepositoryImpl implements CustomUserRepository
{
	private final EntityManager entityManager;

	@Inject
	public UserRepositoryImpl(EntityManager entityManager)
	{
		this.entityManager = entityManager;
	}

	@Override
	public User findByEmailAndPassword(String email, String password)
	{
		JPQLQueryFactory factory = new JPAQueryFactory(this.entityManager);
		User user = factory
			.from(QUser.user)
			.select(QUser.user)
			.where(QUser.user.email.eq(email))
			.fetchFirst();
		if ((user != null) && (user.checkPassword(password)))
			return user;
		else
			return null;
	}
}
