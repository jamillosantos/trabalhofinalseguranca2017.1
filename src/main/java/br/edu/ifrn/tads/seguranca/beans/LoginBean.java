package br.edu.ifrn.tads.seguranca.beans;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.ifrn.tads.seguranca.model.User;
import br.edu.ifrn.tads.seguranca.service.UserService;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class LoginBean {

	@Getter
	@Setter
	private String email;

	@Getter
	@Setter
	private String password;

	@Inject
	private UserService _service;

	public void logar() {

	System.out.println("email = " + email + "senha " + password);
		User user = null;
		user = this._service.findByEmailAndPassword(this.email,this.password);
		if(user != null){
			System.out.println("usuario ok   ================================== ");
		}else{
			System.out.println("usuario null ================================== ");
		}

	}

}
