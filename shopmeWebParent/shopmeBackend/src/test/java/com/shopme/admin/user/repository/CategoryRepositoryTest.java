package com.shopme.admin.user.repository;

import com.shopme.admin.Categories.CategoriesRepository;
import com.shopme.common.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EntityScan("com.shopme.common")
@Rollback(value = false)
public class CategoryRepositoryTest {

    @Autowired
    CategoriesRepository repository;

    @Test
    public void getCategories(){
        Category category = repository.findByName("Electronics");
        System.out.println(category.toString());
    }

    @Test
    public void createMainCategory(){
        Category parent = new Category();
        parent.setName("Computers");

        repository.save(parent);
    }


    @Test
    public void runTest(){
        Category parent = repository.findByName("Computer Components");

        Category category = new Category();
        category.setName("Memory");
        category.setParent(parent);
        repository.save(category);
    }


    @Test
    public void getCategoryTree(){
        List<Category> categoryList = repository.findAll();

        Set<Category> categorySet = categoryList.stream().filter(category -> Objects.isNull(category.getParent())).collect(Collectors.toSet());

        List<String> tree = new ArrayList<>();

        categorySet.stream().forEach(category -> getCategoryTree(category, tree, ""));

        tree.forEach(System.out::println);
    }

    public void getCategoryTree(Category category, List<String> tree,  String branchString){
        tree.add(branchString+category.getName());
        Set<Category> categories = category.getChildren();

        for(Category category1 : categories){
            getCategoryTree(category1, tree, "-- "+branchString);
        }
        return;
    }
}
