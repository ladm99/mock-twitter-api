package com.cooksys.assessment1.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name="user_table")
public class User {
	
	
	//<---------Internal Fields--------->//
	@Id
	@GeneratedValue
	private Long id;
	
	@Embedded
	private Credentials credentials;
	
	@Column(nullable = false)
	@CreationTimestamp
	private Timestamp joined;
	
	private boolean deleted = false;

	@Embedded
	private Profile profile;
	
	
	//<---------Outgoing Relationships--------->//
	
	//tweets
	@OneToMany(mappedBy = "author")
	private List<Tweet> tweets;
	
	//followers
	@ManyToMany
	@JoinTable(name = "followers_following", 
	  joinColumns = @JoinColumn(name = "follower_id"), 
	  inverseJoinColumns = @JoinColumn(name = "following_id"))
	private List<User> followers;

	@ManyToMany(mappedBy = "followers")
	private List<User> following;

	//likes
	@ManyToMany
	@JoinTable(name = "user_likes", 
	  joinColumns = @JoinColumn(name = "user_id"), 
	  inverseJoinColumns = @JoinColumn(name = "tweet_id"))
	private List<Tweet> likedTweets;

	//mentions
	@ManyToMany
	@JoinTable(name = "user_mentions", 
	  joinColumns = @JoinColumn(name = "user_id"), 
	  inverseJoinColumns = @JoinColumn(name = "tweet_id"))
	private List<Tweet> mentions;

	
	
	
}
