package net.sourceforge.squirrel_sql.client.session.objectstree.tablepanel;
/*
 * Copyright (C) 2001 Colin Bell
 * colbell@users.sourceforge.net
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
import net.sourceforge.squirrel_sql.fw.sql.ITableInfo;

import net.sourceforge.squirrel_sql.client.session.objectstree.objectpanel.BaseObjectPanelTab;

/**
 * Base class for tabs to the added to <TT>TablePanel</TT>. If you are
 * writing a class for a tab to be added to <TT>TablePanel</TT> you don't
 * have to inherit from this class (only implement <TT>ITablePanelTab</TT>)
 * but it has convenience methods.
 *
 * @author  <A HREF="mailto:colbell@users.sourceforge.net">Colin Bell</A>
 */
public abstract class BaseTablePanelTab extends BaseObjectPanelTab implements ITablePanelTab {
    /**
     * Set the <TT>ITableInfo</TT> object that specifies the table that
     * is to have its information displayed.
     *
     * @param    value  <TT>ITableInfo</TT> object that specifies the currently
     *                  selected table. This can be <TT>null</TT>.
     */
    public void setTableInfo(ITableInfo value) {
    	setDatabaseObjectInfo(value);
    }

    /**
     * Retrieve the current <TT>ITableInfo</TT> object.
     *
     * @return    Current <TT>ITableInfo</TT> object.
     */
    public final ITableInfo getTableInfo() {
        return (ITableInfo)getDatabaseObjectInfo();
    }
}
