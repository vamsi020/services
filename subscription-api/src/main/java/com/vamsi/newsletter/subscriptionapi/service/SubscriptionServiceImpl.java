package com.vamsi.newsletter.subscriptionapi.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vamsi.newsletter.subscriptionapi.usermodel.Subscriber;
import com.vamsi.newsletter.subscriptionapi.usermodel.SubscriberRepository;
import com.vamsi.newsletter.subscriptionapi.util.TimeframeConditions;

/**
 * 
 * @author Vamsi Krihna
 * 
 *         Subscription service implementation
 *
 */
@Service
public class SubscriptionServiceImpl implements SubscriptionService {

	@Autowired
	private SubscriberRepository subscriberRepository;

	@Override
	public void addUser(Subscriber user) {
		subscriberRepository.save(user);
	}

	@Override
	public Subscriber getUser(String email) {
		Optional<Subscriber> user = subscriberRepository.findById(email);
		return user.get();
	}

	@Override
	public Map<String, Object> getAllUsers() {
		Map<String, Object> usersMap = new HashMap<String, Object>();
		ArrayList<Subscriber> userList = new ArrayList<Subscriber>();
		Iterable<Subscriber> userItr = subscriberRepository.findAll();
		userItr.forEach(userList::add);
		usersMap.put("users", userList);
		return usersMap;
	}

	@Override
	public Map<String, Object> getUsersBySubscription(boolean subscribedUsers) {
		Map<String, Object> usersMap = new HashMap<String, Object>();
		List<Subscriber> filteredUserList = subscriberRepository.findAllUsersBySubscription(subscribedUsers);
		usersMap.put("users", filteredUserList);
		return usersMap;
	}

	@Override
	public Map<String, Object> getAllUsersByDate(Date requestDate, TimeframeConditions condition) {
		Map<String, Object> usersMap = new HashMap<String, Object>();
		List<Subscriber> filteredUserList = null;
		switch (condition) {
		case AFTER_DATE:
			filteredUserList = subscriberRepository.findAllUsersAfterDate(requestDate);
			break;
		case BEFORE_DATE:
			filteredUserList = subscriberRepository.findAllUsersBeforeDate(requestDate);
			break;
		default:
			break;
		}
		usersMap.put("users", filteredUserList);
		return usersMap;
	}

	@Override
	public Map<String, Object> getUsersBySubscriptionAndDate(Date requestDate, boolean subscribedUsers,
			TimeframeConditions condition) {
		Map<String, Object> usersMap = new HashMap<String, Object>();
		List<Subscriber> filteredUserList = null;
		switch (condition) {
		case AFTER_DATE:
			filteredUserList = subscriberRepository.findAllUsersBySubscriptionAndAfterDate(requestDate,
					subscribedUsers);
			break;
		case BEFORE_DATE:
			filteredUserList = subscriberRepository.findAllUsersBySubscriptionAndBeforeDate(requestDate,
					subscribedUsers);
			break;
		default:
			break;
		}
		usersMap.put("users", filteredUserList);
		return usersMap;
	}

}
