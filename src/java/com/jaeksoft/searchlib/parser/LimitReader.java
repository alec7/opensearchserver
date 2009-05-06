/**   
 * License Agreement for Jaeksoft OpenSearchServer
 *
 * Copyright (C) 2008-2009 Emmanuel Keller / Jaeksoft
 * 
 * http://www.jaeksoft.com
 * 
 * This file is part of Jaeksoft OpenSearchServer.
 *
 * Jaeksoft OpenSearchServer is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 * Jaeksoft OpenSearchServer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Jaeksoft OpenSearchServer. 
 *  If not, see <http://www.gnu.org/licenses/>.
 **/

package com.jaeksoft.searchlib.parser;

import java.io.IOException;
import java.io.Reader;

public class LimitReader extends Reader {

	private boolean isComplete;
	private Reader reader;
	private long limit;

	public LimitReader(Reader reader, long limit) {
		this.reader = reader;
		this.limit = limit;
		this.isComplete = true;
	}

	public boolean isComplete() {
		return isComplete;
	}

	@Override
	public void close() throws IOException {
		reader.close();
	}

	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
		int r = reader.read(cbuf, off, len);
		if (r == -1)
			return r;
		limit -= r;
		if (limit < 0) {
			isComplete = false;
			throw new LimitException();
		}
		return r;
	}

}
