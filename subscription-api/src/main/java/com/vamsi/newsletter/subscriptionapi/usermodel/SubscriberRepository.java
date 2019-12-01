package com.vamsi.newsletter.subscriptionapi.usermodel;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * 
 * @author Vamsi Krihna
 * Subscriber repository 
 * Extends CrudRepository - provide basic CRUD operations.
 * 
 */
public interface SubscriberRepository extends CrudRepository<Subscriber, String> {
	
	/**
	 * Query to find all users by subscription
	 * 
	 * @param subscribed - true/false
	 * @return List of subscribed or unsubscribed users
	 */
	@Query("SELECT s FROM Subscriber s WHERE s.subscribed = ?1")
	List<Subscriber> findAllUsersBySubscription(Boolean subscriptionFilter);
	
	/**
	 * Query to find users before given date
	 * 
	 * @param reqDate
	 * @return List of users before given given date
	 */
	@Query("SELECT s FROM Subscriber s WHERE s.lastUpdatedTimestamp < ?1")
	List<Subscriber> findAllUsersBeforeDate(Date reqDate);
	
	/**
	 * Query to find users after given date
	 * 
	 * @param reqDate
	 * @return List of users after given given date
	 */
	@Query("SELECT s FROM Subscriber s WHERE s.lastUpdatedTimestamp >= ?1")
	List<Subscriber> findAllUsersAfterDate(Date reqDate);
	
	/**
	 * Query to find subscribed or unsubscribed users before given date
	 * 
	 * @param reqDate
	 * @param subscribed - true/false
	 * @return List of subscribed or unsubscribed users before given date
	 */
	@Query("SELECT s FROM Subscriber s WHERE s.lastUpdatedTimestamp < ?1 AND s.subscribed = ?2")
	List<Subscriber> findAllUsersBySubscriptionAndBeforeDate(Date reqDate, Boolean subscriptionFilter);
	
	/**
	 * Query to find subscribed or unsubscribed users after given date
	 * 
	 * @param reqDate
	 * @param subscribed - true/false
	 * @return list of subscribed or unsubscribed users after given date
	 */
	@Query("SELECT s FROM Subscriber s WHERE s.lastUpdatedTimestamp >= ?1 AND s.subscribed = ?2")
	List<Subscriber> findAllUsersBySubscriptionAndAfterDate(Date reqDate, Boolean subscriptionFilter);

}
