package br.edu.ifrn.tads.seguranca.model;

import javax.persistence.*;

import br.edu.ifrn.tads.seguranca.utils.Hash;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.*;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "users")
@ToString(of = { "id", "name", "email","permissao" })
@NoArgsConstructor
public class User {
	@Id
	@Getter
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Setter
	@Getter
	private String name;

	@Setter
	@Getter
	private String email;

	@Getter
	private String password;
	
	@Setter
	@Getter
	private String permissao;

	@Getter
	private String salt;

	private String publicKey;

	@Getter
	@Singular
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Collection<Group> groups = new ArrayList<>();

	public User(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.permissao = permissao;
		this.generateSalt();
		this.setPassword(password);
	}

	private String generateSalt() {
		return Hash.hash(UUID.randomUUID().toString());
	}

	private String generatePassword(String value) {
		if (this.salt == null)
			this.salt = this.generateSalt();
		return Hash.hash(this.salt + value + value + this.salt + this.salt);
	}

	public boolean checkPassword(String plainPassword) {
		return this.generatePassword(plainPassword).equals(this.getPassword());
	}

	public void setPassword(String value) {
		this.salt = this.generateSalt();
		this.password = this.generatePassword(value);
	}

	public void setPublicKey(PublicKey value) throws NoSuchAlgorithmException, InvalidKeySpecException {
		KeyFactory fact = KeyFactory.getInstance("RSA");
		X509EncodedKeySpec spec = fact.getKeySpec(value, X509EncodedKeySpec.class);
		this.publicKey = Base64.getEncoder().encodeToString(spec.getEncoded());
	}

	public PublicKey getPublicKey() {
		try {
			byte[] data = Base64.getDecoder().decode(this.publicKey);
			X509EncodedKeySpec spec = new X509EncodedKeySpec(data);
			KeyFactory fact = KeyFactory.getInstance("RSA");
			return fact.generatePublic(spec);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			return null;
		}
	}

	public boolean getHasPublicKey() {
		return (this.publicKey != null) && (!this.publicKey.isEmpty());
	}

	public String getPublicKeyString() {
		return this.publicKey;
	}
}
