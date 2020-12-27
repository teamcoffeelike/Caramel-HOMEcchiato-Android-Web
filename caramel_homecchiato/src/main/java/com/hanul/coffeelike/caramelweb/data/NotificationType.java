package com.hanul.coffeelike.caramelweb.data;

import org.springframework.lang.Nullable;

public enum NotificationType{
	REACTION,
	LIKE,
	FOLLOW;

	@Override public String toString(){
		return name().toLowerCase();
	}

	@Nullable public static NotificationType fromString(String string){
		try{
			return valueOf(string.toUpperCase());
		}catch(IllegalArgumentException ex){
			return null;
		}
	}
}
