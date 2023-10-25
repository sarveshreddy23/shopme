package com.shopme.admin.Categories;

import com.shopme.common.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CategoriesController {

    @Autowired
    CategoriesService categoriesService;

    @Autowired
    CategoriesRepository repository;

    @GetMapping("categories")
    public String showCategories(Model model){
        List<Category> categoryList = categoriesService.findAllCategories();
        model.addAttribute("categoryList", categoryList);
        return "categories/categories";
    }

    @GetMapping("categories/new")
    public String createCategory(Model model){
        Category category = new Category();
        List<Category> categoriesByHierarchy = categoriesService.getAllCategoriesByHierarchy();
        model.addAttribute("categoriesByHierarchy", categoriesByHierarchy);
        model.addAttribute("category", category);
        model.addAttribute("pageTitle", "Create New Category");
        return "categories/category-form";
    }

    @PostMapping(value = "/categories/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String saveCategory( CategoryDTO categoryDTO, @RequestParam("fileUpload") MultipartFile file, RedirectAttributes redirectAttributes){
        System.out.println("Category :::::: "+categoryDTO.toString());
        if(categoryDTO.getId()==0){
            Category category= new Category();
                category.setName(categoryDTO.getName());
                category.setAlias(categoryDTO.getAlias());
                category.setParent(repository.findById(categoryDTO.getParent()).get());
                repository.save(category);
                redirectAttributes.addFlashAttribute("message", "New Category added successfully");
        }

        return "redirect:/categories";
    }

    @GetMapping("/categories/delete/{categoryId}")
    public String  deleteCategory(@PathVariable("categoryId") int id, RedirectAttributes redirectAttributes){
        repository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Category deleted Successfully");
        return "redirect:/categories";
    }

    @GetMapping("/categories/edit/{categoryId}")
    public String  editCategory(@PathVariable("categoryId") int id, RedirectAttributes redirectAttributes, Model model){
        Category category = repository.findById(id).get();
        CategoryDTO categoryDTO = new CategoryDTO(category);
        List<Category> categoriesByHierarchy = categoriesService.getAllCategoriesByHierarchy();
        model.addAttribute("categoriesByHierarchy", categoriesByHierarchy);
        model.addAttribute("category", categoryDTO);
        model.addAttribute("pageTitle", "Create New Category");
        return "categories/category-form";
    }
}
