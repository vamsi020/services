package com.vamsi.newsletter.subscriptionapi.usermodel;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SubscriberRepository extends CrudRepository<Subscriber, String> {
	
	@Query("SELECT s FROM Subscriber s WHERE s.isSubscribed = ?1")
	List<Subscriber> findAllUsersBySubscription(Boolean subscribed);
	
	@Query("SELECT s FROM Subscriber s WHERE s.lastUpdatedTimestamp = ?1")
	List<Subscriber> findAllUsersByDate(Date reqDate);
	
	@Query("SELECT s FROM Subscriber s WHERE s.lastUpdatedTimestamp < ?1")
	List<Subscriber> findAllUsersBeforeDate(Date reqDate);
	
	@Query("SELECT s FROM Subscriber s WHERE s.lastUpdatedTimestamp >= ?1")
	List<Subscriber> findAllUsersAfterDate(Date reqDate);
	
	@Query("SELECT s FROM Subscriber s WHERE s.lastUpdatedTimestamp = ?1 AND s.isSubscribed = ?2")
	List<Subscriber> findAllUsersBySubscriptionAndDate(Date reqDate, Boolean subscribed);
	
	@Query("SELECT s FROM Subscriber s WHERE s.lastUpdatedTimestamp < ?1 AND s.isSubscribed = ?2")
	List<Subscriber> findAllUsersBySubscriptionAndBeforeDate(Date reqDate, Boolean subscribed);
	
	@Query("SELECT s FROM Subscriber s WHERE s.lastUpdatedTimestamp >= ?1 AND s.isSubscribed = ?2")
	List<Subscriber> findAllUsersBySubscriptionAndAfterDate(Date reqDate, Boolean subscribed);

}
