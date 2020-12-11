package com.hanul.coffeelike.caramelweb.data;

import java.sql.Timestamp;

public interface Notification {
	NotificationType type();
	
	int notifiedUserId();
	Timestamp notifyDate();
	
	Timestamp readDate();
}
