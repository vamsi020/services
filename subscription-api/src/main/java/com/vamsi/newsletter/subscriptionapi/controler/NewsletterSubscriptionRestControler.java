package com.vamsi.newsletter.subscriptionapi.controler;

import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Email;

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
import com.vamsi.newsletter.subscriptionapi.util.ErrorResponcePojo;
import com.vamsi.newsletter.subscriptionapi.util.GenericUtil;
import com.vamsi.newsletter.subscriptionapi.util.ResponcePojo;
import com.vamsi.newsletter.subscriptionapi.util.SuccessResponcePojo;
import com.vamsi.newsletter.subscriptionapi.util.TimeframeConditions;

@RestController
@RequestMapping("/newsletter")
public class NewsletterSubscriptionRestControler {

	@Autowired
	private SubscriptionService subscriptionService;

	@PostMapping(path = "/subscribe", consumes = "application/json")
	public ResponcePojo subscribe(@Valid @RequestBody Subscriber user) {
		try {
			user.setSubscribed(true);
			subscriptionService.addUser(user);
			return new SuccessResponcePojo("Success");
		} catch (Exception e) {
			e.printStackTrace();
			return new ErrorResponcePojo(e.getMessage());
		}
	}

	@PutMapping(path = "/unsubscribe", consumes = "application/json")
	public ResponcePojo unSubscribe(@Valid @RequestBody Subscriber user) {
		try {
			user.setSubscribed(false);
			subscriptionService.addUser(user);
			return new SuccessResponcePojo("Success");
		} catch (Exception e) {
			e.printStackTrace();
			return new ErrorResponcePojo(e.getMessage());
		}
	}

	@RequestMapping(path = "/user", params = "email")
	public ResponcePojo getUser(@Valid @RequestParam @Email String email) {
		try {
			return new SuccessResponcePojo(subscriptionService.getUser(email));
		} catch (Exception e) {
			e.printStackTrace();
			return new ErrorResponcePojo(e.getMessage());
		}
	}

	@RequestMapping(path = "/user/isSubscribed", params = "email")
	public boolean isUserSubscribed(@Valid @RequestParam @Email String email) {
		Subscriber user = subscriptionService.getUser(email);
		return user.isSubscribed();
	}

	@RequestMapping(path = "/users")
	public ResponcePojo getUsers(
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
			return new SuccessResponcePojo(users);
		} catch (Exception e) {
			e.printStackTrace();
			return new ErrorResponcePojo(e.getMessage());
		}
	}
}
