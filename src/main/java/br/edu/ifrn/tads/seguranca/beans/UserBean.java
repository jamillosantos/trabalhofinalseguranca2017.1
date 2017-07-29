package br.edu.ifrn.tads.seguranca.beans;

import br.edu.ifrn.tads.seguranca.model.User;
import br.edu.ifrn.tads.seguranca.service.CertService;
import br.edu.ifrn.tads.seguranca.service.UserService;
import lombok.Getter;
import lombok.Setter;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.PrivateKey;

@Named
public class UserBean
{
	@Inject
	private UserService _service;

	@Inject
	private CertService _certService;

	@Getter
	private User newUser = new User();

	@Getter
	@Setter
	private String newUserPassword;
	


	public void saveNewUser()
	{
		try
		{
			this.newUser.setPassword(this.newUserPassword);
			this._service.save(this.newUser);

			this.newUser = new User();
			this.newUserPassword = "";
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public PrivateKey generateKey(User user)
	{
		try
		{
			return this._certService.generate(user);
		}
		catch (Exception e)
		{
			return null;
		}
	}

	public void delete(User user)
	{
		this._service.delete(user);
	}

	public Iterable<User> getList()
	{
		return this._service.findAll();
	}

	public User findById(long id)
	{
		return this._service.findOne(id);
	}
	
	

	public void privateKeyToResponse(PrivateKey privateKey, HttpServletResponse response)
	{
		try
		{
			int len = this._certService.privateKeyToStream(privateKey, response.getOutputStream());
			response.setContentLength(len);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
