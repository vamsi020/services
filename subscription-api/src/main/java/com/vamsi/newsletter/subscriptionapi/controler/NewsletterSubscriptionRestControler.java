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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vamsi.newsletter.subscriptionapi.service.SubscriptionService;
import com.vamsi.newsletter.subscriptionapi.usermodel.Subscriber;
import com.vamsi.newsletter.subscriptionapi.util.ErrorResponsePojo;
import com.vamsi.newsletter.subscriptionapi.util.GenericUtil;
import com.vamsi.newsletter.subscriptionapi.util.ResponsePojo;
import com.vamsi.newsletter.subscriptionapi.util.SuccessResponsePojo;
import com.vamsi.newsletter.subscriptionapi.util.TimeframeConditions;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @author Vamsi Krihna
 * 
 *         Newsletter subscription controller API calls
 * 
 */
@Api
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
	@ApiOperation(value = "Subscribe to newsletter API", 
			response = ResponsePojo.class, 
			notes = "API accepts JSON as a request body. Only Email perameter is mandatory")
	@PostMapping(path = "/subscribe", consumes = "application/json")
	public ResponsePojo subscribe(
			@ApiParam(value = "Subscriber object to save in DB", 
				required = true, 
				example = "{\"email\":\"vamsi020@gmail.com\",\"name\":\"Vamsi\"}") 
			@Valid @RequestBody Subscriber user) {
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
	 * Un-subscribe to newsletter. PUT call - Error message if user not present
	 * 
	 * @param email
	 * @return ResponcePojo - Success or error message
	 */
	@ApiOperation(value = "Unsubscribe to newsletter API", 
			response = ResponsePojo.class, 
			notes = "API accepts JSON as a request body. Only Email perameter is required")
	@PutMapping(path = "/unsubscribe", consumes = "application/json")
	public ResponsePojo unSubscribe(
			@ApiParam(value = "UnSubscriber object to modify DB", 
				required = true, 
				example = "{\"email\":\"vamsi020@gmail.com\"}") 
			@Valid @RequestBody Subscriber user) {
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
	@ApiOperation(value = "Get user from email", 
			response = ResponsePojo.class, 
			notes = "Send email as a query perameter to get the user details.")
	@RequestMapping(path = "/user", method = RequestMethod.GET, params = "email")
	public ResponsePojo getUser(
			@ApiParam(value = "Email to get user", required = true) 
			@RequestParam @Pattern(regexp = GenericUtil.EMAIL_REGEX) String email) {
		try {
			return new SuccessResponsePojo(subscriptionService.getUser(email));
		} catch (Exception e) {
			e.printStackTrace();
			return new ErrorResponsePojo(e.getMessage());
		}
	}

	/**
	 * Is given user subscribed or not; 
	 * 
	 * True if user exists and subscribed or else false.
	 * 
	 * @param email
	 * @return boolean - true/false
	 */
	@ApiOperation(value = "Is user subscribed to newsletter API", 
			response = Boolean.class, 
			notes = "Send email as a query perameter to know if user is subscribed or not.")
	@RequestMapping(path = "/user/isSubscribed", method = RequestMethod.GET, params = "email")
	public ResponsePojo isUserSubscribed(
			@ApiParam(value = "Email to verify user subscription", required = true) 
			@RequestParam @Pattern(regexp = GenericUtil.EMAIL_REGEX) String email) {
		try {
			Subscriber user = subscriptionService.getUser(email);
			return new SuccessResponsePojo(user.isSubscribed());
		} catch (Exception e) {
			e.printStackTrace();
			return new ErrorResponsePojo(e.getMessage());
		}
	}

	/**
	 * 
	 * Returns all users depends on query parameters
	 * 
	 * * No Parameters - all users in DB 
	 * * Only subscribed - if true returns all subscribed users; if false returns unsubscribed users 
	 * * Only queryDate and condition - Returns user list before or after the given date 
	 * * All Parameters - Returns list of subscribed or unsubscribed users before or after the given date
	 * 
	 * @param queryDate  - optional - format "yyyy-MM-dd HH:mm:ss"
	 * @param subscribed - optional - true or false
	 * @param condition  - optional - AFTER_DATE or BEFORE_DATE
	 * @return ResponcePojo; users list object assigned to data key
	 * 
	 */
	@ApiOperation(value = "Users list API", response = ResponsePojo.class, 
			notes = "Returns all users depends on query parameters passed to the request."
			+ "<br> * No Parameters - all users in DB"
			+ "<br> * Only subscribed - if true returns all subscribed users; if false returns unsubscribed users "
			+ "<br> * Only queryDate and condition - Returns user list before or after the given date "
			+ "<br> * All Parameters - Returns list of subscribed or unsubscribed users before or after the given date")
	@RequestMapping(path = "/users", method = RequestMethod.GET)
	public ResponsePojo getUsers(
			@ApiParam(format = "yyyy-MM-dd HH:mm:ss", value = "Date string filter to get users list", example = "2019-10-29 00:00:00") 
			@Valid @RequestParam(value = "queryDate", required = false) 
			@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") 
			Optional<String> queryDate,
			@ApiParam(value = "Subscription filter to get users list", allowableValues = "true, false") 
			@RequestParam(value = "subscribed", required = false) 
			Optional<String> subscribed,
			@ApiParam(value = "Timeframe filter to get users list",	allowableValues = "AFTER_DATE, BEFORE_DATE") 
			@RequestParam(value = "condition", required = false) 
			Optional<String> condition) {
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
