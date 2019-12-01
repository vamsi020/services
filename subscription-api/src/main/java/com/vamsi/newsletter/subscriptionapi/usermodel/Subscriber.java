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

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author Vamsi Krihna
 * 
 * Subscriber Model with ORM
 * Email is primary key and should be valid 
 *
 */
@ApiModel(value = "Subsribers details")
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
	@ApiModelProperty(value = "User Email - Primary key")
	private String email;

	@ApiModelProperty(value = "User Name")
	private String name;

	@NotNull
	@ApiModelProperty(value = "Is user subscribed", hidden = true)
	private boolean subscribed;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "User first subsctiption Timestamp", hidden = true)
	private Date subscriptionTimestamp;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "User last updated Timestamp", hidden = true)
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
		return subscribed;
	}

	public void setSubscribed(boolean subscribed) {
		this.subscribed = subscribed;
	}

	public Date getSubscriptionTimestamp() {
		return subscriptionTimestamp;
	}

	public Date getLastUpdatedTimestamp() {
		return lastUpdatedTimestamp;
	}
}
