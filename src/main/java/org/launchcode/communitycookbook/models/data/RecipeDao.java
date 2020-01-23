package org.launchcode.communitycookbook.models.data;

import org.launchcode.communitycookbook.models.Recipe;
import org.launchcode.communitycookbook.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
    @Transactional
    public interface RecipeDao extends CrudRepository<Recipe, Integer> {
        default Recipe findOne(Integer id) {
            return (Recipe) findById(id).orElse(null);
        }

        List<Recipe> findByName(String name);

        List<Recipe> findByUser(User user);

    }

