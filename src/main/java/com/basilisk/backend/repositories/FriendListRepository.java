package com.basilisk.backend.repositories;

import com.basilisk.backend.models.FriendList;
import com.basilisk.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendListRepository extends JpaRepository<FriendList,Long>
{
    FriendList findByUser(User user);
}
