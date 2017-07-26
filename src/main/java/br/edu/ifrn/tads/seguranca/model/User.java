package br.edu.ifrn.tads.seguranca.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.edu.ifrn.tads.seguranca.utils.Hash;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
public class User
{
	@Id
	@Setter
	public String email;

	@Setter
	public String name;

	public String password;

	@Setter
	public String salt;

	private String generateSalt()
	{
		return Hash.hash(UUID.randomUUID().toString());
	}

	private String generatePassword(String value)
	{
		return Hash.hash(this.salt + value + value + this.salt + this.salt);
	}

	public void setPassword(String value)
	{
		this.salt = this.generateSalt();
		this.password = this.generatePassword(value);
	}
}
