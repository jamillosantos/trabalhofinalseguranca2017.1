package br.edu.ifrn.tads.seguranca.repository;

import br.edu.ifrn.tads.seguranca.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>
{
	User findFirstByEmail(String email);
}
