package com.cooksys.assessment1.entities;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Where;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Where(clause = "deleted=false")
public class Tweet {
	
	//<---------Internal Fields--------->//
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
//	@JoinColumn(name = "user_id")
	private User author;

	@Column(nullable = false)
	@CreationTimestamp
	private Timestamp posted;
	
	private boolean deleted = false;
	
	private String content;
	
	
	//inReplyTo relationships
	@ManyToOne
	@JoinColumn(name = "in_reply_to")
	private Tweet inReplyTo;
	
	@OneToMany(mappedBy = "inReplyTo")
	private List<Tweet> replies;
	
	//repostOf relationships
	@ManyToOne
	@JoinColumn(name = "repost_of")
	private Tweet repostOf;
	
	@OneToMany(mappedBy = "repostOf")
	private List<Tweet> reposts;
	
	
	//<---------Outgoing Relationships--------->//
	
	//hashtag
	@ManyToMany(cascade=CascadeType.MERGE)
	@JoinTable(name = "tweet_hashtags", 
			  joinColumns = @JoinColumn(name = "tweet_id"), 
			  inverseJoinColumns = @JoinColumn(name = "hashtag_id"))
	private List<Hashtag> hashtags;
	
	//liked by user
	@ManyToMany(mappedBy = "likedTweets")
	private List<User> likedBy;
	
	//mentioned by user
	@ManyToMany(mappedBy = "mentions")
	private List<User> mentionedBy;
	
	

}
