/**
 * Vosao CMS. Simple CMS for Google App Engine.
 * Copyright (C) 2009 Vosao development team
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 * email: vosao.dev@gmail.com
 */

package org.vosao.business.mq.message;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.vosao.business.mq.AbstractMessage;
import org.vosao.business.mq.Topic;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public class PageMessage extends AbstractMessage {

	private Map<String, Set<Long>> pages;
	
	public PageMessage(Topic topic) {
		super();
		setTopic(topic.name());	
	}
	
	public PageMessage(Topic topic, String pageURL, 
			Long pageId) {
		this(topic);
		pages = new HashMap<String, Set<Long>>();
		addPage(pageURL, pageId);
	}

	public void addPage(String url, Long id) {
		if (!pages.containsKey(url)) {
			pages.put(url, new HashSet<Long>());
		}
		pages.get(url).add(id);
	}
	
	public Map<String, Set<Long>> getPages() {
		return pages;
	}
}