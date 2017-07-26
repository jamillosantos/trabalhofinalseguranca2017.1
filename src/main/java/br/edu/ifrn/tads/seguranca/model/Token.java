package br.edu.ifrn.tads.seguranca.model;

import br.edu.ifrn.tads.seguranca.utils.Hash;
import lombok.*;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "tokens")
@Getter
@ToString
@NoArgsConstructor
public class Token
{
	public static final Integer RENEW_MINUTES = 60;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@ManyToOne
	private User user;

	private String token;

	private String salt;

	@Setter
	@Column(nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime expiresAt;

	public Token(User user, String ip, String proxy, String userAgent)
	{
		this.user = user;
		this.salt = Hash.hash(UUID.randomUUID().toString());
		this.token = this.generateToken(ip, proxy, userAgent);
		this.expiresAt = DateTime.now().plusMinutes(RENEW_MINUTES);
	}

	private String generateToken(String ip, String proxy, String userAgent)
	{
		return Hash.hash(this.salt + ip + proxy + ip + userAgent + this.salt);
	}

	public boolean check(String ip, String proxy, String userAgent)
	{
		return this.getToken().equals(this.generateToken(ip, proxy, userAgent));
	}
}
