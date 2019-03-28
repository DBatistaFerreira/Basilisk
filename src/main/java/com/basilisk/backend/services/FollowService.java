package com.basilisk.backend.services;

import com.basilisk.backend.models.Follow;
import com.basilisk.backend.models.User;
import com.basilisk.backend.repositories.FollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FollowService {

    private FollowRepository followRepository;

    // Constructor
    @Autowired
    public FollowService(FollowRepository followRepository) {
        this.followRepository = followRepository;
    }
    // Public Service methods

    // Returns the followings of a user.
    public List<User> getAllUserFollowings(User user) {
        return followRepository.getAllByFollower(user).stream().map(Follow::getFollowed).collect(Collectors.toList());
    }

    // Returns the followers of a user.
    public List<User> getAllUserFollowers(User user) {
        return followRepository.getAllByFollowed(user).stream().map(Follow::getFollower).collect(Collectors.toList());
    }

    public void follow(User follower, User followed) {
        createFollow(new Follow(follower, followed));
    }

    public void unfollow(User follower, User followed) {
        deleteFollow(retrieveFollowByFollowerAndFollowed(follower, followed));
    }

    public void editFollow(Follow follow) {
        updateFollow(follow);
    }

    public Follow getFollowById(long id) {
        return retrieveFollow(id);
    }

    // private CRUD Operations
    private void createFollow(Follow follow) {
        followRepository.save(follow);
    }

    private void deleteFollow(Follow follow) {
        followRepository.delete(follow);
    }

    private void updateFollow(Follow follow) {
        followRepository.save(follow);
    }

    private Follow retrieveFollow(long id) {
        return followRepository.getOne(id);
    }

    private Follow retrieveFollowByFollowerAndFollowed(User follower, User followed) {
        return followRepository.getByFollowerAndAndFollowed(follower, followed);
    }
}
