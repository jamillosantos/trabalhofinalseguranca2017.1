package br.edu.ifrn.tads.seguranca.beans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.ifrn.tads.seguranca.model.User;
import br.edu.ifrn.tads.seguranca.service.UserService;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class LoginBean
{

	@Getter
	@Setter
	private String email;

	@Getter
	@Setter
	private String password;

	@Inject
	private UserService _service;

	public String logar()
	{
		try
		{
			System.out.println("email = " + email + "senha " + password);
			User user = null;
			user = this._service.findByEmailAndPassword(this.email, this.password);
			if (user != null)
			{
				UsuarioLogadoBean.setUserLogado(user);
				return "users?faces-redirect=true";
			}
			else
			{
				FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Email ou senha incorreto"));
				System.out.println("usuario null ================================== ");
			}
		}
		catch (Exception e)
		{
			FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Ocorreu um erro"));
		}

		return "";

	}


}
