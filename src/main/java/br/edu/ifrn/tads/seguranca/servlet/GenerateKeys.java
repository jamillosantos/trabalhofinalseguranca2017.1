package br.edu.ifrn.tads.seguranca.servlet;

import br.edu.ifrn.tads.seguranca.beans.UserBean;
import br.edu.ifrn.tads.seguranca.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.PrivateKey;

@Controller
public class GenerateKeys
{
	@Inject
	private UserBean userBean;

	@RequestMapping(value = "/generate-keys/{id}", method = RequestMethod.GET)
	protected void dispatch(
		@PathVariable("id") Long id,
		HttpServletRequest request,
		HttpServletResponse response
	)
	{
		response.addHeader("content-disposition", "attachment; filename=privatekey.pem");
		response.setContentType("application/octet-stream");
		System.out.println("userBean");
		System.out.println(this.userBean);
		System.out.println("request.getParameter(\"id\")");
		System.out.println(request.getParameter("id"));
		User user = userBean.findById(id);
		PrivateKey privateKey = userBean.generateKey(user);
		userBean.privateKeyToResponse(privateKey, response);
	}
}
