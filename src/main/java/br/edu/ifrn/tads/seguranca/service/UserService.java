package br.edu.ifrn.tads.seguranca.service;

import br.edu.ifrn.tads.seguranca.model.User;
import br.edu.ifrn.tads.seguranca.repository.UserRepository;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class UserService extends Service<User, String>
{
	@Inject
	public UserService(UserRepository repository)
	{
		super(repository);
	}
}
