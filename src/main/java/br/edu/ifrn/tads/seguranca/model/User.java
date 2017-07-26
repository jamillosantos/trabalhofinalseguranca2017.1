package br.edu.ifrn.tads.seguranca.model;

import javax.persistence.*;

import br.edu.ifrn.tads.seguranca.utils.Hash;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@ToString
@NoArgsConstructor
public class User
{
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Setter
	private String name;

	@Setter
	private String email;

	private String password;

	private String salt;

	@Singular
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Collection<Group> groups = new ArrayList<>();

	public User(String name, String email, String password)
	{
		this.name = name;
		this.email = email;
		this.generateSalt();
		this.setPassword(password);
	}

	private String generateSalt()
	{
		return Hash.hash(UUID.randomUUID().toString());
	}

	private String generatePassword(String value)
	{
		if (this.salt == null)
			this.salt = this.generateSalt();
		return Hash.hash(this.salt + value + value + this.salt + this.salt);
	}

	public boolean checkPassword(String plainPassword)
	{
		return this.generatePassword(plainPassword).equals(this.getPassword());
	}

	public void setPassword(String value)
	{
		this.salt = this.generateSalt();
		this.password = this.generatePassword(value);
	}
}
