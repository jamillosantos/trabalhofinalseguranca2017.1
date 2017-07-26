package br.edu.ifrn.tads.seguranca.service;

import br.edu.ifrn.tads.seguranca.model.Token;
import br.edu.ifrn.tads.seguranca.model.User;
import br.edu.ifrn.tads.seguranca.repository.TokenRepository;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class TokenService extends Service<Token, Long>
{
	@Inject
	public TokenService(TokenRepository repository)
	{
		super(repository);
	}

	public void renew(Token token)
	{
		token.setExpiresAt(token.getExpiresAt().plusMinutes(Token.RENEW_MINUTES));
	}

	public Token findByToken(User user, String token)
	{
		return ((TokenRepository)this.getRepository()).findByUserAndToken(user, token);
	}
}
