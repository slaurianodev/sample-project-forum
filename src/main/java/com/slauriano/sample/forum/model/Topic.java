package com.slauriano.sample.forum.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Topic {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id")
    private Long id;
    
    @Column(name="title")
    private String title;
    
    @Column(name="description")
    private String description;
    
    @Column(name="creation_date")
    private Date creationDate;
    
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    protected Topic() {}

    public Topic(String title, String description, Date creationDate) {
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
    }

    public Long getId() {
        return id;
    }

	public void setId(Long id) {
		this.id = id;
	}
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreationDate() {
		return formatDate(creationDate);
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
    public String toString() {
        return String.format(
                "Topic[id=%d, title=%s, description=%s, creationDate=%s",
                id, title, description,creationDate);
    }
	
	private String formatDate(Date date){
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		return dateFormat.format(date);
	}
}
