package com.shopme.common.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "Categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 128, nullable = false, unique = true)
    private String name;
    @Column(length = 128, nullable = true)
    private String alias;

    @Column(length = 128, nullable = true)
    private String image;
    private boolean enable;

    @OneToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private Set<Category> children = new HashSet<>();

    public Category(int id) {
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
        this.alias = name;
    }
}
