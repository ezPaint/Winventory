package com.simoncomputing.app.winventory.domain;

public enum EventType {
	ADMIN, MANUAL, SYSTEM;
	
	
	public static String getString(EventType type)
	{
		switch (type)
		{
		case ADMIN:
			return "ADMIN";
		case MANUAL:
			return "MANUAL";
		
		case SYSTEM:
		    return "SYSTEM";  //added by controllers
		}
		return "INVALID";
	}
}
