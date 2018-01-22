package com.slauriano.sample.forum.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Comment {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "description")
	private String description;

	@Column(name = "creation_date")
	private Date creationDate;

	@ManyToOne
	@JoinColumn(name = "topic_id")
	private Topic topic;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	protected Comment(){}
	
	public Comment(String description, Date creationDate){
		this.description = description;
		this.creationDate = creationDate;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return String.format("Comment=[id='%s', description='%s', creadionDate='%s', topicId='%s', userId='%s']", 
				id, description, creationDate, topic.getId(), user.getId());
	}
}
