package br.edu.ifrn.tads.seguranca.beans;

import br.edu.ifrn.tads.seguranca.model.User;
import br.edu.ifrn.tads.seguranca.service.UserService;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class UserBean
{
	@Inject
	private UserService _service;

	public Iterable<User> getList()
	{
		return this._service.findAll();
	}
}
