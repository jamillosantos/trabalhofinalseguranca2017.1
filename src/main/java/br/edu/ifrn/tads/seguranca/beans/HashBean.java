package br.edu.ifrn.tads.seguranca.beans;

import br.edu.ifrn.tads.seguranca.model.User;
import br.edu.ifrn.tads.seguranca.service.CertService;
import br.edu.ifrn.tads.seguranca.service.FileService;
import br.edu.ifrn.tads.seguranca.service.UserService;
import lombok.Getter;
import lombok.Setter;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@Named
public class HashBean
{
	@Inject
	private FileService _service;

	@Getter
	@Setter
	private String hashCode;

	@Getter
	@Setter
	private Part file;

	@Getter
	private String calculatedHash;

	public void verify()
	{
		try
		{
			String s[] = this.hashCode.split("[^a-zA-Z0-9]+");
			StringBuilder sb = new StringBuilder();
			for (String piece : s)
				sb.append(piece);
			this.hashCode = sb.toString();
			this.calculatedHash = this._service.hashStream(file.getInputStream());
			if ((!this.hashCode.isEmpty()))
			{
				if (this.hashCode.equals(this.calculatedHash))
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "O arquivo está OK."));
				else
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "O arquivo está corrompido."));
			}
		}
		catch (IOException | NoSuchAlgorithmException e)
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Deu uma treta no processo. Mensagem: " + e.getMessage()));
		}
	}
}
