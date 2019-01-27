package com.basilisk.backend.services;

import com.basilisk.backend.models.FriendList;
import com.basilisk.backend.models.User;
import com.basilisk.backend.repositories.FriendListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendListService {

    private FriendListRepository friendListRepository;

    @Autowired
    public FriendListService(FriendListRepository friendListRepository) {
        this.friendListRepository = friendListRepository;
    }

    public void createFriendList(FriendList friendList) {
        friendListRepository.save(friendList);
    }

    public FriendList retrieveFriendListByUser(User user) {
        return friendListRepository.findByUser(user);
    }

    public FriendList retrieveFriendList(long id) {
        return friendListRepository.getOne(id);
    }

    public void updateFriendList(FriendList friendList) {
        friendListRepository.save(friendList);
    }

    public void deleteFriendList(FriendList friendList) {
        friendListRepository.delete(friendList);
    }

}
