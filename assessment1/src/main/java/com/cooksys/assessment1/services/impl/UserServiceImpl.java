package com.cooksys.assessment1.services.impl;

import com.cooksys.assessment1.dtos.CredentialsDto;
import com.cooksys.assessment1.dtos.TweetResponseDto;
import com.cooksys.assessment1.dtos.UserRequestDto;
import com.cooksys.assessment1.dtos.UserResponseDto;
import com.cooksys.assessment1.entities.Credentials;
import com.cooksys.assessment1.entities.Profile;
import com.cooksys.assessment1.entities.Tweet;
import com.cooksys.assessment1.entities.User;
import com.cooksys.assessment1.exceptions.BadRequestException;
import com.cooksys.assessment1.exceptions.NotFoundException;
import com.cooksys.assessment1.mappers.CredentialsMapper;
import com.cooksys.assessment1.mappers.TweetMapper;
import com.cooksys.assessment1.mappers.UserMapper;
import com.cooksys.assessment1.repositories.TweetRepository;
import com.cooksys.assessment1.repositories.UserRepository;
import com.cooksys.assessment1.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CredentialsMapper credentialsMapper;
    private final TweetRepository tweetRepository;
    private final TweetMapper tweetMapper;

    private void validateUserRequest(UserRequestDto userRequestDto){
    	if(userRequestDto.getCredentials() == null || userRequestDto.getProfile() == null) {
    		throw new BadRequestException("Request body missing either credentials or profile.");
    	}
		if(userRequestDto.getProfile().getEmail() == null || userRequestDto.getCredentials().getPassword() == null || userRequestDto.getCredentials().getUsername() == null){
			throw new BadRequestException("Request body missing required fields");
		}

	}
    
    private class TweetPostTimeComparator implements Comparator<Tweet> {
		@Override
		public int compare(Tweet tweet1, Tweet tweet2) {
			/**Compares two tweets, reverse chronological order
			 * Inputs: Tweet tweet1, Tweet tweet2
			 * Output: Int
			 */
			long t1 = tweet1.getPosted().getTime();
		    long t2 = tweet2.getPosted().getTime();
		    if(t2 > t1)
		            return -1;
		    else if(t1 > t2)
		            return 1;
		    else
		            return 0;
		}
    }
    
    @Override
	public List<UserResponseDto> getUsers() {
		return userMapper.entitiesToResponseDTOs(userRepository.findAllByDeletedFalse());
	}
    
	@Override
	public UserResponseDto createUser(UserRequestDto userRequestDto) {
		validateUserRequest(userRequestDto);
		
		User user = userMapper.requestDtoToEntity(userRequestDto);
		Optional<User> check = userRepository.findByCredentials(user.getCredentials());
		if(!check.isEmpty() && !check.get().isDeleted())
			throw new BadRequestException("User with this username already exists");

		if(!check.isEmpty() && check.get().isDeleted()) {
			user = check.get();
			user.setDeleted(false);
		}
		return userMapper.entityToResponseDto(userRepository.saveAndFlush(user));
	}

	@Override
	public UserResponseDto updateUsername(UserRequestDto userRequestDto, String username) {
		/**Updates the profile in the requestDto with the username supplied in the url
		 * Inputs: UserRequestDto, username
		 * Output: UserResponseDto
		 */
		//check for missing profile
		if(userRequestDto.getProfile() == null) {
			throw new BadRequestException("The request body must contain a profile");
		}
		
		//check if user exists
		Optional<User> queryResult = userRepository.findByCredentials(userMapper.userRequestDtoToEntity(userRequestDto).getCredentials());
		if(queryResult.isEmpty()) {
			throw new NotFoundException("Your credentials cannot be found in the database");
		}
		User user = queryResult.get();
		
		//check if user is deleted
		if(user.isDeleted()) {
			throw new BadRequestException("Username " + user.getCredentials().getUsername() + " has been deleted.");
		}

		//update username
		Credentials newCredentials = user.getCredentials();
		newCredentials.setUsername(username);
		user.setCredentials(newCredentials);
		
		//update profile
		Profile newProfile = userMapper.requestDtoToEntity(userRequestDto).getProfile();
		
		//validate and set newProfile
		if(newProfile.getEmail() != null) {
			user.setProfile(newProfile);
		}
		
		//save to database
		return userMapper.entityToResponseDto(userRepository.saveAndFlush(user));
	}
	
	@Override
	public void followUser(CredentialsDto credentialsRequestDto, String username) {
		/**Sets the user with credentials form the body to follow the user given in the url username.
		 * Inputs: CredentialsDto, String username
		 * Output: void
		 */
		//check if user to follow exists
		Optional<User> queryResult = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
		if(queryResult.isEmpty()) {
			throw new NotFoundException("A user with username " + username + " could not be found");
		}
		User userToFollow = queryResult.get();
		
		//check if credentials submitted exist
		System.out.println(credentialsRequestDto);
		queryResult = userRepository.findByCredentials(credentialsMapper.requestDtoEntity(credentialsRequestDto));
		if(queryResult.isEmpty()) {
			throw new NotFoundException("Your credentials cannot be found in the database");
		}
		User user = queryResult.get();
		
		//check if user is already following userToFollow
		if(userToFollow.getFollowers().contains(user)) {
			throw new BadRequestException("You are already following the user with username: " + username + "!" );
		}
		
		//Make sure user and userToFollow are different
		if(userToFollow.getId() == user.getId()) {
			throw new BadRequestException("You cannot follow yourself!" );
		}
		
		//update followers and following fields, insert into database.
		List<User> userIsFollowing = user.getFollowing();
		List<User> userToFollowFollowers = userToFollow.getFollowers();	
		userIsFollowing.add(userToFollow);
		userToFollowFollowers.add(user);
		user.setFollowing(userIsFollowing);
		userToFollow.setFollowers(userToFollowFollowers);
		userRepository.saveAndFlush(user);
		userRepository.saveAndFlush(userToFollow);
		
		return;
	}

	@Override
	public void unfollowUser(CredentialsDto credentialsRequestDto, String username) {
		/**Sets the user with credentials from the body to unfollow the user given in the url username.
		 * Inputs: CredentialsDto, String username
		 * Output: void
		 */
		//check if user to unfollow exists
		Optional<User> queryResult = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
		if(queryResult.isEmpty()) {
			throw new NotFoundException("A user with username " + username + " could not be found");
		}
		User userToUnfollow = queryResult.get();
		
		//check if credentials submitted exist
		queryResult = userRepository.findByCredentials(credentialsMapper.requestDtoEntity(credentialsRequestDto));
		if(queryResult.isEmpty()) {
			throw new NotFoundException("Your credentials cannot be found in the database");
		}
		User user = queryResult.get();
		
		//check if user is not yet following userToFollow
		if(!userToUnfollow.getFollowers().contains(user)) {
			throw new BadRequestException("You are not yet following the user with username: " + username + "!" );
		}
		
		//Make sure user and userToFollow are different
		if(userToUnfollow.getId() == user.getId()) {
			throw new BadRequestException("You cannot unfollow yourself!" );
		}
		
		//update followers and following fields, insert into database.
		List<User> userIsFollowing = user.getFollowing();
		List<User> userToUnfollowFollowers = userToUnfollow.getFollowers();	
		userIsFollowing.remove(userToUnfollow);
		userToUnfollowFollowers.remove(user);
		user.setFollowing(userIsFollowing);
		userToUnfollow.setFollowers(userToUnfollowFollowers);
		userRepository.saveAndFlush(user);
		userRepository.saveAndFlush(userToUnfollow);
		
		return;
	}

	@Override
	public UserResponseDto getUser(String username) {
		/**Gets the user with username.
		 * Inputs: String username
		 * Output: UserResponseDto
		 */
		//check if user exists in db
		Optional<User> queryResult = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
		if(queryResult.isEmpty()) {
			throw new NotFoundException("The username: " + username + " could not be found");
		}
		User user = queryResult.get();
		
		return userMapper.entityToResponseDto(user);
	}

	@Override
	public List<TweetResponseDto> getUserTweets(String username) {
		/**Get a list of all of the tweets made by the user.
		 * Input: String username
		 * Output: List<TweetResponseDto>
		 */
		
		//validate and get user from db
		Optional<User> userQueryResult = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
		if(userQueryResult.isEmpty()) {
			throw new NotFoundException("The username: " + username + " could not be found");
		}
		User user = userQueryResult.get();
		
		return tweetMapper.entitiesToResponseDTOs(user.getTweets());
	}
	
	@Override
	public List<TweetResponseDto> getUserMentions(String username) {
		/**Get a list of all of the tweets that mention the user.
		 * Input: String username
		 * Output: List<TweetResponseDto>
		 */
		
		//validate and get user from db
		Optional<User> userQueryResult = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
		if(userQueryResult.isEmpty()) {
			throw new NotFoundException("The username: " + username + " could not be found");
		}
		User user = userQueryResult.get();
		
		return tweetMapper.entitiesToResponseDTOs(user.getMentions());
	}

	@Override
	public List<TweetResponseDto> getUserFeed(String username) {
		/**Get a list of all of the tweets made by the user + user follows. Sort in reverse chronological order.
		 * Input: String username
		 * Output: List<TweetResponseDto>
		 */
		
		//validate and get user from db
		Optional<User> userQueryResult = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
		if(userQueryResult.isEmpty()) {
			throw new NotFoundException("The username: " + username + " could not be found");
		}
		User user = userQueryResult.get();
		
		List<User> userFollows = user.getFollowing();
		
		//Add user's tweets and user follows tweets to a list
		List<Tweet> tweets = user.getTweets();
		for(User follow : userFollows) {
			tweets.addAll(follow.getTweets());
		}
		
		//sort list
		Collections.sort(tweets, new TweetPostTimeComparator());
		
		return tweetMapper.entitiesToResponseDTOs(tweets);
	}

	@Override
	public UserResponseDto deleteUser(CredentialsDto credentials, String username) {
		/**Delete the user while maintaining records.
		 * Inputs: CredentialsDto credentials, String username
		 * Output: UserReponseDto user
		 */
		
		//validate and get user from db
		Optional<User> userQueryResult = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
		if(userQueryResult.isEmpty()) {
			throw new NotFoundException("The username: " + username + " could not be found");
		}
		User user = userQueryResult.get();
		
		//check credentials
		System.out.println(user.getCredentials());
		System.out.println(credentialsMapper.requestDtoEntity(credentials));
		if(!user.getCredentials().equals(credentialsMapper.requestDtoEntity(credentials))) {
			throw new BadRequestException("Your credentials do not match the profile you are trying to delete." ); 
		}
		
		user.setDeleted(true);
		userRepository.saveAndFlush(user);
		
		return userMapper.entityToResponseDto(user);
	}

	@Override
	public List<UserResponseDto> getFollowers(String username){
		Optional<User> user = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
		if(user.isEmpty()) {
			throw new NotFoundException("The username: " + username + " could not be found");
		}
		List<User> followers = user.get().getFollowers().stream().filter(follower -> !follower.isDeleted()).collect(Collectors.toList());

		return userMapper.entitiesToResponseDTOs(followers);
	}

	@Override
	public List<UserResponseDto> getFollowing(String username){
		Optional<User> user = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
		if(user.isEmpty()) {
			throw new NotFoundException("The username: " + username + " could not be found");
		}
		List<User> following = user.get().getFollowing().stream().filter(follow -> !follow.isDeleted()).collect(Collectors.toList());
		return userMapper.entitiesToResponseDTOs(following);
	}
}
