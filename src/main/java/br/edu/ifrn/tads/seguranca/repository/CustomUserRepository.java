package br.edu.ifrn.tads.seguranca.repository;

import br.edu.ifrn.tads.seguranca.model.User;

public interface CustomUserRepository
{
	User findByEmailAndPassword(String email, String password);
}
