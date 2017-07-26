package br.edu.ifrn.tads.seguranca.service;

import br.edu.ifrn.tads.seguranca.model.Group;
import br.edu.ifrn.tads.seguranca.repository.GroupRepository;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class GroupService extends Service<Group, Long>
{
	@Inject
	public GroupService(GroupRepository repository)
	{
		super(repository);
	}
}
