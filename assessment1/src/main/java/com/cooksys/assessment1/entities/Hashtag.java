package com.cooksys.assessment1.entities;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Hashtag {
	//<---------Internal Fields--------->//
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String label;
	
	@Column(nullable = false)
	@CreationTimestamp
	private Timestamp firstUsed;
	
	@UpdateTimestamp
	private Timestamp lastUsed;
	
	
	//<---------Outgoing Relationships--------->//
	@Column(nullable = false)
	@ManyToMany(mappedBy = "hashtags", cascade = CascadeType.ALL)
	private List<Tweet> tweets;
}

