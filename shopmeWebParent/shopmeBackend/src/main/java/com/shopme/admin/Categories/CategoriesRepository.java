package com.shopme.admin.Categories;

import com.shopme.common.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesRepository  extends JpaRepository<Category, Integer> {

    public Category findByName(String name);

}
