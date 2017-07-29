package br.edu.ifrn.tads.seguranca.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;

import br.edu.ifrn.tads.seguranca.model.User;
import lombok.Getter;
import lombok.Setter;

@SessionScoped
@Named
public class UsuarioLogadoBean {
	@Getter
	@Setter
	private static User userLogado = null;
	
//	@Getter
//	@Setter
//	private static boolean  hasUserLogado = false;
	
	
	public boolean getHasUserLogado(){
		System.out.println(userLogado != null);
		return userLogado != null;
	}
	
	public boolean getUserIsAdministrador(){
		if(userLogado != null){
			return "Administrador".equals(userLogado.getPermission());
		}
		return false;
		
	}
}
