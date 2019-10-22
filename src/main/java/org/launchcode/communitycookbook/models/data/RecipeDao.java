package org.launchcode.communitycookbook.models.data;

import org.launchcode.communitycookbook.models.Recipe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

    @Repository
    @Transactional
    public interface RecipeDao extends CrudRepository<Recipe, Integer> {
    }
