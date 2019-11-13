package org.launchcode.communitycookbook.models.data;

import org.launchcode.communitycookbook.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserDao extends JpaRepository<User, Integer> {
    User findByEmail(String email);
}