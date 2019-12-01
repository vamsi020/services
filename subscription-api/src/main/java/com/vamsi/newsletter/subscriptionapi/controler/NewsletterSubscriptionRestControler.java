package com.vamsi.newsletter.subscriptionapi.controler;

import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vamsi.newsletter.subscriptionapi.service.SubscriptionService;
import com.vamsi.newsletter.subscriptionapi.usermodel.Subscriber;
import com.vamsi.newsletter.subscriptionapi.util.ErrorResponsePojo;
import com.vamsi.newsletter.subscriptionapi.util.GenericUtil;
import com.vamsi.newsletter.subscriptionapi.util.ResponsePojo;
import com.vamsi.newsletter.subscriptionapi.util.SuccessResponsePojo;
import com.vamsi.newsletter.subscriptionapi.util.TimeframeConditions;

/**
 * 
 * @author Vamsi Krihna 
 * 
 * Newsletter subscription controller API calls
 * 
 */
@RestController
@RequestMapping("/newsletter")
public class NewsletterSubscriptionRestControler {

	@Autowired
	private SubscriptionService subscriptionService;

	/**
	 * Subscribe to newsletter. POST call - Adds entry to DB
	 * 
	 * @param user
	 * @return ResponcePojo - Success or error message
	 */
	@PostMapping(path = "/subscribe", consumes = "application/json")
	public ResponsePojo subscribe(@Valid @RequestBody Subscriber user) {
		try {
			user.setSubscribed(true);
			subscriptionService.addUser(user);
			return new SuccessResponsePojo("Success");
		} catch (Exception e) {
			e.printStackTrace();
			return new ErrorResponsePojo(e.getMessage());
		}
	}

	/**
	 * Unsubscribe to newsletter. PUT call - Error message if user not present
	 * 
	 * @param email
	 * @return ResponcePojo - Success or error message
	 */
	@PutMapping(path = "/unsubscribe", consumes = "application/json")
	public ResponsePojo unSubscribe(@Valid @RequestBody Subscriber user) {
		try {
			Subscriber subscriber = subscriptionService.getUser(user.getEmail());
			subscriber.setSubscribed(false);
			subscriptionService.addUser(subscriber);
			return new SuccessResponsePojo("Success");
		} catch (Exception e) {
			e.printStackTrace();
			return new ErrorResponsePojo(e.getMessage());
		}
	}

	/**
	 * Get user form email sting Error message if user is not present
	 * 
	 * @param email
	 * @return ResponcePojo; user object assigned to data key.
	 */
	@RequestMapping(path = "/user", params = "email")
	public ResponsePojo getUser(@RequestParam @Pattern(regexp = GenericUtil.EMAIL_REGEX) String email) {
		try {
			return new SuccessResponsePojo(subscriptionService.getUser(email));
		} catch (Exception e) {
			e.printStackTrace();
			return new ErrorResponsePojo(e.getMessage());
		}
	}

	/**
	 * Is given user subscribed or not True if user exists and subscribed or else
	 * false.
	 * 
	 * @param email
	 * @return boolean - true/false 
	 */
	@RequestMapping(path = "/user/isSubscribed", params = "email")
	public boolean isUserSubscribed(@RequestParam @Pattern(regexp = GenericUtil.EMAIL_REGEX) String email) {
		try {
			Subscriber user = subscriptionService.getUser(email);
			return user.isSubscribed();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * Returns all users depends on query perameters No Perameters - all users in DB
	 * 
	 * * Only subscribed - if true returns all subscribed users; if false returns unsubscribed users 
	 * * Only queryDate and condition - Returns user list before or after the given date 
	 * * All perams - Returns list of subscribed or unsubscribed users before or after the given date
	 * 
	 * @param queryDate  - optional - format "yyyy-MM-dd HH:mm:ss"
	 * @param subscribed - optional - true or false
	 * @param condition  - optional - AFTER_DATE or BEFORE_DATE
	 * @return ResponcePojo; users list object assigned to data key
	 */
	@RequestMapping(path = "/users")
	public ResponsePojo getUsers(
			@Valid @RequestParam(value = "queryDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Optional<String> queryDate,
			@RequestParam(value = "subscribed", required = false) Optional<String> subscribed,
			@RequestParam(value = "condition", required = false) Optional<String> condition) {
		try {
			Map<String, Object> users = null;
			if (queryDate.isPresent() && subscribed.isPresent() && condition.isPresent()) {
				users = subscriptionService.getUsersBySubscriptionAndDate(GenericUtil.castStringToDate(queryDate.get()),
						Boolean.parseBoolean(subscribed.get()), TimeframeConditions.valueOf(condition.get()));
			} else if (queryDate.isPresent() && condition.isPresent() && !subscribed.isPresent()) {
				users = subscriptionService.getAllUsersByDate(GenericUtil.castStringToDate(queryDate.get()),
						TimeframeConditions.valueOf(condition.get()));
			} else if (subscribed.isPresent() && !queryDate.isPresent() && !condition.isPresent()) {
				users = subscriptionService.getUsersBySubscription(Boolean.parseBoolean(subscribed.get()));
			} else {
				users = subscriptionService.getAllUsers();
			}
			return new SuccessResponsePojo(users);
		} catch (Exception e) {
			e.printStackTrace();
			return new ErrorResponsePojo(e.getMessage());
		}
	}
}
