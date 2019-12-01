package com.vamsi.newsletter.subscriptionapi.usermodel;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vamsi.newsletter.subscriptionapi.util.GenericUtil;

/**
 * 
 * @author Vamsi Krihna
 * 
 * Subscriber Model with ORM
 * Email is primary key and should be valid 
 *
 */
@Entity
public class Subscriber {

	protected Subscriber() {
	}

	public Subscriber(String email, String name) {
		this.email = email;
		this.name = name;
	}

	@Id
	@Email(regexp = GenericUtil.EMAIL_REGEX)
	private String email;

	private String name;

	@NotNull
	private boolean isSubscribed;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date subscriptionTimestamp;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdatedTimestamp;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSubscribed() {
		return isSubscribed;
	}

	public void setSubscribed(boolean isSubscribed) {
		this.isSubscribed = isSubscribed;
	}

	public Date getSubscriptionTimestamp() {
		return subscriptionTimestamp;
	}

	public Date getLastUpdatedTimestamp() {
		return lastUpdatedTimestamp;
	}
}
