package br.edu.ifrn.tads.seguranca.repository;

import br.edu.ifrn.tads.seguranca.model.Token;
import br.edu.ifrn.tads.seguranca.model.User;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<Token, Long>
{
	Token findByUserAndToken(User user, String token);
}
