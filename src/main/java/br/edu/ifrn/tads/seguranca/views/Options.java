package br.edu.ifrn.tads.seguranca.views;

import br.edu.ifrn.tads.seguranca.service.Service;
import com.google.common.collect.Lists;
import org.omnifaces.converter.ListConverter;
import javax.faces.convert.Converter;

import javax.faces.model.SelectItem;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static org.hibernate.internal.util.collections.ArrayHelper.toList;

public abstract class Options<T, ID extends Serializable>
{

	private boolean listCreated = false;

	private Service<T, ID> service;

	@Inject
	public void setService(Service<T, ID> service)
	{
		this.service = service;
	}

	protected List<T> fillList()
	{
		return Lists.newArrayList(this.service.findAll());
	}

	protected void verify()
	{
		if (!this.listCreated)
		{
			List<T> newList = fillList();
			setList(newList);
			this.listCreated = true;

			if (this.list != null)
			{
				this.options = createOptions();
				this.emptyOptions = createEmptyOptions(this.options);
			}
		}
	}

	protected void clear()
	{
		this.emptyOptions = null;
		this.options = null;

		this.listConverter = null;

		this.listCreated = false;
	}

	protected abstract Object key(T e);

	private transient ListConverter listConverter;

	public Converter getListConverter()
	{
		verify();

		if (this.listConverter == null)
		{
			this.listConverter = new ListConverter();
			this.listConverter.setList(getList());
		}

		return this.listConverter;
	}

	private List<T> list;

	public void setList(List<T> list)
	{
		this.list = list;

		clear();
	}

	public final List<T> getList()
	{
		verify();

		return this.list;
	}

	private List<SelectItem> options;

	protected final List<SelectItem> createOptions()
	{
		List<SelectItem> result = new ArrayList<SelectItem>();
		for (T e : this.list)
		{
			Object key = key(e);
			result.add(new SelectItem(key, label(e)));
		}
		return result;
	}

	public final List<SelectItem> getOptions()
	{
		verify();

		return this.options;
	}

	private List<SelectItem> emptyOptions;

	protected final List<SelectItem> createEmptyOptions(List<SelectItem> options)
	{
		List<SelectItem> result = new ArrayList<SelectItem>(options.size() + 1);
		result.add(new SelectItem("", "", ""));
		result.addAll(options);
		return result;
	}

	public final List<SelectItem> getEmptyOptions()
	{
		verify();

		return this.emptyOptions;
	}

	public abstract String label(T e);

}
