package br.edu.ifrn.tads.seguranca.service;

import br.edu.ifrn.tads.seguranca.model.User;
import br.edu.ifrn.tads.seguranca.repository.UserRepository;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class UserService extends Service<User, Long>
{
	@Inject
	public UserService(UserRepository repository)
	{
		super(repository);
	}

	public User findByEmailAndPassword(String email, String password)
	{
		User user = ((UserRepository) this.getRepository()).findFirstByEmail(email);
		System.out.println("email " + email + "SENHA" + password );
		System.out.println("EMAIL " + user.getEmail() + " SENHA " + user.getPassword() );
		user.mostrarSenha();
		if ((user != null) && (user.checkPassword(password)))
			return user;
		else
			return null;
	}
}
