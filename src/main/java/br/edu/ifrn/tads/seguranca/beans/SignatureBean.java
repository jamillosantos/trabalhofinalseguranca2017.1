package br.edu.ifrn.tads.seguranca.beans;

import br.edu.ifrn.tads.seguranca.model.User;
import br.edu.ifrn.tads.seguranca.service.CertService;
import br.edu.ifrn.tads.seguranca.service.UserService;
import lombok.Getter;
import lombok.Setter;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.security.PrivateKey;

@Named
public class SignatureBean
{
	@Inject
	private UserService _service;

	@Inject
	private CertService _certService;

	@Getter
	@Setter
	private String signatureCode;

	@Getter
	@Setter
	private User user;

	@Getter
	@Setter
	private Part file;

	private Boolean checked = null;

	@Inject
	private UserService _userService;

	public User getUser()
	{
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		try
		{
			Long id = Long.parseLong(request.getParameter("id"));
			this.user = this._userService.findOne(id);
			return this.user;
		}
		catch (Throwable e)
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível localizar o usuário."));
		}
		return null;
	}

	public void verify()
	{
		try
		{
			String s[] = this.signatureCode.split("[^a-zA-Z0-9+\\-=/_]+");
			StringBuilder sb = new StringBuilder();
			for (String piece : s)
				sb.append(piece);
			System.out.println(sb.toString());
			if (this._certService.checkSignature(this.getUser(), sb.toString(), file.getInputStream()))
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "A assinatura corresponde ao arquivo e ao usuário."));
			else
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "A assinatura não confere."));
		}
		catch (IOException e)
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Deu uma treta no processo. Mensagem: " + e.getMessage()));
		}
	}
}
