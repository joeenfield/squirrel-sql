package net.sourceforge.squirrel_sql.client.plugin;
/*
 * Copyright (C) 2001-2002 Colin Bell
 * colbell@users.sourceforge.net
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
public class PluginInfo
{
	private String _pluginClassName;
	private IPlugin _plugin;
	private boolean _loaded;

	/**
	 * Default ctor.
	 * 
	 * Should only be used to treat as a Javabean.
	 */
	public PluginInfo()
	{
		super();
	}

	PluginInfo(String pluginClassName) throws IllegalArgumentException
	{
		super();
		if (pluginClassName == null)
		{
			throw new IllegalArgumentException("Null pluginClassName passed");
		}

		_pluginClassName = pluginClassName;
	}

	public void assignFrom(PluginInfo pi) throws IllegalArgumentException
	{
		if (pi == null)
		{
			throw new IllegalArgumentException("Null PluginInfo passed");
		}

		setPlugin(pi.getPlugin());
		setLoaded(pi.isLoaded());
	}

	public String getPluginClassName()
	{
		return _pluginClassName;
	}

	public boolean isLoaded()
	{
		return _loaded;
	}

	/**
	 * Returns the name by which this plugin is uniquely identified.
	 *
	 * @return	the name by which this plugin is uniquely identified.
	 */
	public String getInternalName()
	{
		return _plugin.getInternalName();
	}

	/**
	 * Returns the descriptive name for this plugin.
	 *
	 * @return	the descriptive name for this plugin.
	 */
	public String getDescriptiveName()
	{
		return _plugin.getDescriptiveName();
	}

	/**
	 * Returns the authors name.
	 *
	 * @return	the authors name.
	 */
	public String getAuthor()
	{
		return _plugin.getAuthor();
	}

	/**
	 * Returns the home page for this plugin.
	 *
	 * @return	the home page for this plugin.
	 */
	public String getWebSite()
	{
		return _plugin.getWebSite();
	}

	/**
	 * Returns the current version of this plugin.
	 *
	 * @return	the current version of this plugin.
	 */
	public String getVersion()
	{
		return _plugin.getVersion();
	}

	/**
	 * Return the <TT>IPlugin</TT>. Warning this will be
	 * <TT>null</TT> if the plugin could not be
	 * instantiated.
	 *
	 * @return  the <TT>IPlugin</TT>.
	 */
	public IPlugin getPlugin()
	{
		return _plugin;
	}

	void setPlugin(IPlugin value) throws IllegalArgumentException
	{
		if (value == null)
		{
			throw new IllegalArgumentException("Null IPlugin passed");
		}
		_plugin = value;
	}

	void setLoaded(boolean value)
	{
		_loaded = value;
	}
}
