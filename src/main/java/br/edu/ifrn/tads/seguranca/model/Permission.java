package br.edu.ifrn.tads.seguranca.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Permission
{
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	private String name;
}
