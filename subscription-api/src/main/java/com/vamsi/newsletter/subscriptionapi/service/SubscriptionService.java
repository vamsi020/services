package com.vamsi.newsletter.subscriptionapi.service;

import java.util.Date;
import java.util.Map;

import com.vamsi.newsletter.subscriptionapi.usermodel.Subscriber;
import com.vamsi.newsletter.subscriptionapi.util.TimeframeConditions;

public interface SubscriptionService {

	public void addUser(Subscriber user);

	public Subscriber getUser(String email);

	public Map<String, Object> getAllUsers();

	public Map<String, Object> getUsersBySubscription(boolean subscribedUsers);

	public Map<String, Object> getAllUsersByDate(Date requestDate, TimeframeConditions condition);

	public Map<String, Object> getUsersBySubscriptionAndDate(Date requestDate, boolean subscribedUsers,
			TimeframeConditions condition);

}
