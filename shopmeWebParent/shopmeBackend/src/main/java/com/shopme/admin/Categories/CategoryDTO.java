package com.shopme.admin.Categories;

import com.shopme.common.entity.Category;
import lombok.Data;
import lombok.ToString;

import java.util.Objects;

@Data
@ToString
public class CategoryDTO {
    int id;
    String name;
    String alias;
    int parent;
    boolean enable;

    public CategoryDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.alias = category.getAlias();
        this.parent = (!Objects.isNull(category.getParent()) && category.getParent().getId()>0) ?   category.getParent().getId() : 0;
        this.enable = category.isEnable();
    }
}
