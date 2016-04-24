package org.armitage.inc.AAInfo.dao;

import org.armitage.inc.AAInfo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer>{
    @Query("select u from User u where u.userName= :login")
    public User getByLogin(@Param("login") String login);
}
