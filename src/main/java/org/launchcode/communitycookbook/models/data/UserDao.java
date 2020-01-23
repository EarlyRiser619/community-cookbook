package org.launchcode.communitycookbook.models.data;

import org.launchcode.communitycookbook.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface UserDao extends CrudRepository<User, Integer> {
    User findByEmail(String email);

    default User findOne(Integer id) {
        return (User) findById(id).orElse(null);
    }

    List<User> findByNameOrLastName(String name, String lastName);

}