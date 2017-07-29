package br.edu.ifrn.tads.seguranca;

import br.edu.ifrn.tads.seguranca.model.User;
import br.edu.ifrn.tads.seguranca.service.UserService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class DatabaseSeeder implements ApplicationRunner
{

	@Inject
	private UserService userService;

	public void run(ApplicationArguments args)
	{
		try
		{
			User user = new User("Teste", "teste@ifrn.edu.br", "12345");
			user.setPermission("Administrador");
			this.userService.save(user);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
