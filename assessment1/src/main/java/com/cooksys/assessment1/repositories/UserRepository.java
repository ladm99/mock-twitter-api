package com.cooksys.assessment1.repositories;

import com.cooksys.assessment1.entities.Credentials;
import com.cooksys.assessment1.entities.User;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	List<User> findAllByDeletedFalse();


	List<User> findAllByDeletedTrue();
	Optional<User> findByCredentials(Credentials credentials);

	
	Optional<User> findByCredentialsUsernameAndDeletedFalse(String username);

	
	Optional<User> findByCredentialsAndDeletedFalse(Credentials credentials);

}
