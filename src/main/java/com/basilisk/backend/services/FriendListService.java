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

    //CRUD OPERATIONS
    //Save FriendList to DB
    public void createFriendList(FriendList friendList) {
        friendListRepository.save(friendList);
    }

    //Get FriendList from the DB by user
    public FriendList retrieveFriendListByUser(User user) {
        return friendListRepository.findByUser(user);
    }

    //Get FriendList from the DB by ID
    public FriendList retrieveFriendList(long id) {
        return friendListRepository.getOne(id);
    }

    //Update FriendList into the DB
    public void updateFriendList(FriendList friendList) {
        friendListRepository.save(friendList);
    }

    //Delete FriendList from the DB
    public void deleteFriendList(FriendList friendList) {
        friendListRepository.delete(friendList);
    }

}
