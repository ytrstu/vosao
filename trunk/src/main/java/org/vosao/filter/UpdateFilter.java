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

package org.vosao.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.vosao.update.UpdateException;
import org.vosao.update.UpdateManager;


public class UpdateFilter implements Filter {
    
    private static final Log logger = LogFactory.getLog(UpdateFilter.class);

    private static final String UPDATE_URL = "/update";
    
    private FilterConfig config = null;
    private ServletContext servletContext = null;
  
    public UpdateFilter() {
    }
  
    public void init(FilterConfig filterConfig) throws ServletException {
        config = filterConfig;
        servletContext = config.getServletContext();
    }
    
    public void doFilter(ServletRequest request, ServletResponse response, 
    		FilterChain chain) throws IOException, ServletException {
    	HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpServletResponse httpResponse = (HttpServletResponse)response;
        String url = httpRequest.getServletPath();
        if (url.equals(UPDATE_URL)) {
        	try {
        		UpdateManager updateManager = new UpdateManager();
        		updateManager.update();
            	logger.info("Update was successfully completed.");
        		writeContent(httpResponse, "Update was successfully complete!");
            	return;
        	}
        	catch (UpdateException e) {
        		writeContent(httpResponse, "Errors during update! " 
        				+ e.getMessage());
        		return;
        	}
        }
        chain.doFilter(request, response);
    }
    
    public void destroy() {
    }
    
    private void writeContent(HttpServletResponse response, String content)
    		throws IOException {
    	response.setContentType("text/html");
    	response.setCharacterEncoding("UTF-8");
    	response.getWriter().append("<html><body>" + content + "</body></html>");
    }
    
}
