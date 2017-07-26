package br.edu.ifrn.tads.seguranca.model;

import br.edu.ifrn.tads.seguranca.utils.Hash;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

@Entity
@Table(name = "groups")
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Group
{
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	private String name;

	@Singular
	@ManyToMany
	private Collection<Permission> permissions = new HashSet<>();
}
