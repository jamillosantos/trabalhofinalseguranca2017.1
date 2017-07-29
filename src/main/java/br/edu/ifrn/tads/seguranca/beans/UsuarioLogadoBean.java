package br.edu.ifrn.tads.seguranca.beans;

import javax.faces.bean.SessionScoped;

import br.edu.ifrn.tads.seguranca.model.User;
import lombok.Getter;
import lombok.Setter;

@SessionScoped
public class UsuarioLogadoBean {
	@Getter
	@Setter
	private static User userLogado = null;
	
	
	public boolean hasUserLogado(){
		return UsuarioLogadoBean.getUserLogado() == null;
	}
}
