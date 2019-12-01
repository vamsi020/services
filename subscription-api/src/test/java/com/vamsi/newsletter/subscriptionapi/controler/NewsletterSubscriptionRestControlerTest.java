package com.vamsi.newsletter.subscriptionapi.controler;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import com.vamsi.newsletter.subscriptionapi.usermodel.Subscriber;
import com.vamsi.newsletter.subscriptionapi.util.ResponsePojo;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class NewsletterSubscriptionRestControlerTest {

	@LocalServerPort
	private int PORT;

	@Autowired
	private TestRestTemplate restTemplate;

	Subscriber vamsi = new Subscriber("vamsi020@gmail.com", "vamsi");

	@Test
	public void testSubscribeService() throws Exception {
		HttpStatus response = restTemplate.postForEntity("/newsletter/subscribe", vamsi, ResponsePojo.class)
				.getStatusCode();
		assertThat(response.equals(HttpStatus.OK));
	}

	@Test
	public void testUnSubscribeService() throws Exception {
		vamsi.setSubscribed(false);
		restTemplate.put("/newsletter/unsubscribe", vamsi);
	}

	@Test
	public void testIsSubscribedService() throws Exception {
		HttpStatus response = restTemplate
				.getForEntity("/user/isSubscribed?email=vamsi020@gmail.com", ResponsePojo.class).getStatusCode();
		assertThat(response.equals(HttpStatus.OK));
	}

	@Test
	public void testGetUserService() throws Exception {
		HttpStatus response = restTemplate.getForEntity("/user?email=vamsi020@gmail.com", Subscriber.class)
				.getStatusCode();
		assertThat(response.equals(HttpStatus.OK));
	}

	@Test
	public void testGetAllUsersService() throws Exception {
		HttpStatus response = restTemplate.getForEntity("/newsletter/users", ResponsePojo.class).getStatusCode();
		assertThat(response.equals(HttpStatus.OK));
	}

	@Test
	public void testGetAllSubscribedUsersService() throws Exception {
		HttpStatus response = restTemplate.getForEntity("/newsletter/users?subscribed=false", ResponsePojo.class)
				.getStatusCode();
		assertThat(response.equals(HttpStatus.OK));
	}

	@Test
	public void testGetAllBeforeUsersByDateService() throws Exception {
		HttpStatus response = restTemplate
				.getForEntity("/newsletter/users?queryDate=2019-10-29 00:00:00&condition=BEFORE_DATE",
						ResponsePojo.class)
				.getStatusCode();
		assertThat(response.equals(HttpStatus.OK));
	}

	@Test
	public void testGetAllAfterUsersByDateService() throws Exception {
		HttpStatus response = restTemplate
				.getForEntity("/newsletter/users?queryDate=2019-10-29 00:00:00&condition=AFTER_DATE",
						ResponsePojo.class)
				.getStatusCode();
		assertThat(response.equals(HttpStatus.OK));
	}

	@Test
	public void testGetAllSubscribedAfterUsersByDateService() throws Exception {
		HttpStatus response = restTemplate
				.getForEntity("/newsletter/users?queryDate=2019-10-29 00:00:00&subscribed=false&condition=AFTER_DATE",
						ResponsePojo.class)
				.getStatusCode();
		assertThat(response.equals(HttpStatus.OK));
	}

}
