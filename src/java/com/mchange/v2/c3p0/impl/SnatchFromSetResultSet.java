/*
 * Distributed as part of c3p0 v.0.9.2-pre5
 *
 * Copyright (C) 2012 Machinery For Change, Inc.
 *
 * Author: Steve Waldman <swaldman@mchange.com>
 *
 * This library is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License version 2.1, as 
 * published by the Free Software Foundation.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this software; see the file LICENSE.  If not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 */


package com.mchange.v2.c3p0.impl;

import java.sql.*;
import java.util.Set;
import com.mchange.v2.sql.filter.FilterResultSet;

final class SnatchFromSetResultSet extends FilterResultSet
{
    Set activeResultSets;

    SnatchFromSetResultSet(Set activeResultSets)
    { this.activeResultSets = activeResultSets; }

    public synchronized void setInner(ResultSet inner)
    {
	this.inner = inner;
	activeResultSets.add( inner );
    }
    
    public synchronized void close() throws SQLException
    { 
	inner.close();
	activeResultSets.remove( inner ); 
	inner = null;
    }
}
