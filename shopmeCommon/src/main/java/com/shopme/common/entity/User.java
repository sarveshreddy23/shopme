package com.shopme.common.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 128, nullable = false, unique = true)
    private String email;
    @Column(length = 64, nullable = false)
    private String password;
    @Column(name = "first_name", length = 45, nullable = false)
    private String firstName;
    @Column(name = "last_name", length = 45, nullable = false)
    private String lastName;
    @Column(length = 64, nullable = false)
    private String photo;
    @Column
    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
            @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))

    Set<Role> roles = new HashSet<>();

    public String getUserRoles(){
        String roleNames = roles.stream().map(role->role.getName()).collect(Collectors.joining(","));
       return (roleNames.isBlank() || Objects.isNull(roleNames)) ? "-" : roleNames;
    }

    public boolean getEnabled(){
        return this.enabled;
    }

    public String getPhotoPath(){

        if(!Objects.isNull(photo) && !photo.isBlank())
            return  String.format("/user-photos/%s/%s", id, photo);
        return "/images/default-user.png";
    }

}
