package com.vamsi.newsletter.subscriptionapi.service;

import java.util.Date;
import java.util.Map;

import com.vamsi.newsletter.subscriptionapi.usermodel.Subscriber;
import com.vamsi.newsletter.subscriptionapi.util.TimeframeConditions;

/**
 * 
 * @author Vamsi Krihna
 * 
 *         Subscription Service
 *
 */
public interface SubscriptionService {

	/**
	 * Add user to DB
	 * 
	 * @param user
	 */
	public void addUser(Subscriber user);

	/**
	 * Get user from Email
	 * 
	 * @param email
	 * @return Subscriber
	 */
	public Subscriber getUser(String email);

	/**
	 * Get all users
	 * 
	 * @return A map with list of users
	 */
	public Map<String, Object> getAllUsers();

	/**
	 * Get user by subscription, subscribed or unsubscribed
	 * 
	 * @param subscribedUsers - true/false
	 * @return A map with list of subscribed or unsubscribed users
	 */
	public Map<String, Object> getUsersBySubscription(boolean subscribedUsers);

	/**
	 * Get All users before or after the given date
	 * 
	 * @param requestDate
	 * @param condition   - Enum - AFTER_DATE or BEFORE_DATE
	 * @return A map with list of users before or after the given date.
	 */
	public Map<String, Object> getAllUsersByDate(Date requestDate, TimeframeConditions condition);

	/**
	 * Get All subscribed or unsubscribed users before or after the given date
	 * 
	 * @param requestDate
	 * @param subscribedUsers - true/false
	 * @param condition       - Enum - AFTER_DATE or BEFORE_DATE
	 * @return Returns A map with list of subscribed or unsubscribed users before or
	 *         after the given date.
	 */
	public Map<String, Object> getUsersBySubscriptionAndDate(Date requestDate, boolean subscribedUsers,
			TimeframeConditions condition);

}
