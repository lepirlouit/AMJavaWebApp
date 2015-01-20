/*
 * Copyright 2003-2009 LCM-ANMC, Inc. All rights reserved.
 * This source code is the property of LCM-ANMC, Direction
 * Informatique and cannot be copied or distributed without
 * the formal permission of LCM-ANMC.
 */
package be.pir.am.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import be.pir.am.api.service.AthleteService;
import be.pir.am.api.service.CategoryService;

/**
 * Is used to get the facades to provide the services.
 *
 * @author 7518294 - Benoit de Biolley
 * @version %PR%
 */
public class ServiceLocator {

	private static volatile ServiceLocator _instance;

	private Context initialContext;
	private Map<String, Object> cache;

	protected ServiceLocator() {
		try {
			this.initialContext = new InitialContext();
			this.cache = Collections
					.synchronizedMap(new HashMap<String, Object>());
		} catch (NamingException ne) {
			ne.printStackTrace();
		}
	}

	public static ServiceLocator getInstance() {
		if (_instance == null) {
			_instance = new ServiceLocator();
		}
		return _instance;
	}

	protected static void setInstance(ServiceLocator serviceLocator) {
		_instance = serviceLocator;
	}

	private Object lookupEjb(String ejbName) {
		if (this.cache.containsKey(ejbName)) {
			return this.cache.get(ejbName);
		}
		try {
			Object ejbRef = initialContext
					.lookup("java:app/am-bus-impl-0.0.1-SNAPSHOT/" + ejbName);
			this.cache.put(ejbName, ejbRef);
			return ejbRef;
		} catch (NamingException ne) {
			throw new RuntimeException(ne);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public AthleteService getAthleteService() {
		return (AthleteService) this.lookupEjb("AthleteServiceImpl");
	}

	public CategoryService getCategoryService() {
		return (CategoryService) this.lookupEjb("CategoryServiceImpl");
	}
}
