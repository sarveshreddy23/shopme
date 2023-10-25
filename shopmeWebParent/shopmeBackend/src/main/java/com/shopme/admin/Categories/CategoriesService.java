package com.shopme.admin.Categories;

import com.shopme.common.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoriesService {
    @Autowired
    CategoriesRepository repository;

    public List<Category> findAllCategories(){
        return repository.findAll();
    }

    public List<Category> getAllCategoriesByHierarchy(){
        List<Category> categoryList = repository.findAll();

        Set<Category> categorySet = categoryList.stream().filter(category -> Objects.isNull(category.getParent())).collect(Collectors.toSet());

        List<Category> tree = new ArrayList<>();

        categorySet.stream().forEach(category -> getCategoryTree(category, tree, ""));
        return tree;
    }

    public void getCategoryTree(Category category, List<Category> tree,  String branchString){

        Category hierachyCategory = new Category();
        hierachyCategory.setId(category.getId());
        hierachyCategory.setName(category.getName());
        hierachyCategory.setAlias(branchString+category.getName());

        tree.add(hierachyCategory);
        Set<Category> categories = category.getChildren();

        for(Category category1 : categories){
            getCategoryTree(category1, tree, "-- "+branchString);
        }
        return;
    }

}
