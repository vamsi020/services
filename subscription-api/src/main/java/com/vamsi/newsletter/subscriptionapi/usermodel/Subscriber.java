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

@Entity
public class Subscriber {

	protected Subscriber() {
	}

	public Subscriber(String email, String name, boolean isSubscribed, Date subscriptionTimestamp,
			Date lastUpdatedTimestamp) {
		this.email = email;
		this.name = name;
		this.isSubscribed = isSubscribed;
		this.subscriptionTimestamp = subscriptionTimestamp;
		this.lastUpdatedTimestamp = lastUpdatedTimestamp;
	}

	@Id
	@Email
	private String email;
	private String name;
	@NotNull
	private boolean isSubscribed;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date subscriptionTimestamp;
	
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
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

	public void setSubscriptionTimestamp(Date subscriptionTimestamp) {
		this.subscriptionTimestamp = subscriptionTimestamp;
	}

	public Date getLastUpdatedTimestamp() {
		return lastUpdatedTimestamp;
	}

	public void setLastUpdatedTimestamp(Date lastUpdatedTimestamp) {
		this.lastUpdatedTimestamp = lastUpdatedTimestamp;
	}

}
