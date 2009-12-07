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


function backServiceFailed(e) {
	if (e != null) {
		error('Can\'t connect to server. ' + e + ' ' + e.message + ' ' 
				+ e.code + ' ' + e.msg);
		return true;
	}
	return false;
}

function serviceHandler(serviceFunc) {
	return function() {
		var callback = arguments[0];
		var serviceFuncArgs = arguments;
		serviceFuncArgs[0] = function (r, e) {
			if (backServiceFailed(e)) return;
			callback(r);
		};
		serviceFunc.apply(null, serviceFuncArgs);
	}
}

function setupJSONRpcHooks() {
	for (var serviceName in jsonrpc) {
		if (serviceName.indexOf('Service') != -1 
			&& serviceName.indexOf('FrontService') == -1) {
			for (var methodName in jsonrpc[serviceName]) {
				if (typeof jsonrpc[serviceName][methodName] == 'function') {
					var func = jsonrpc[serviceName][methodName];
					jsonrpc[serviceName][methodName] = serviceHandler(func);
				}
			}
		}
	}
}


/**
 * Backend services.
 */

$(function() {
    initJSONRpcSystem(initBackServices);
});

function initBackServices() {
	setupJSONRpcHooks();
}