package br.edu.ifrn.tads.seguranca.views;

import br.edu.ifrn.tads.seguranca.model.User;

import javax.faces.bean.ViewScoped;
import javax.inject.Named;

@ViewScoped
@Named
public class UserOptions extends Options<User, Long>
{
	@Override
	public String label(User user)
	{
		return user.getName();
	}

	@Override
	protected Object key(User user)
	{
		return user.getId();
	}

}
