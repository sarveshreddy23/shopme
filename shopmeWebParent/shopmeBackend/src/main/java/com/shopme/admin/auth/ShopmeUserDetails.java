package com.shopme.admin.auth;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ShopmeUserDetails implements UserDetails {

    User user;

    public ShopmeUserDetails(User user) {
        this.user = user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roleSet = user.getRoles();
        List<SimpleGrantedAuthority> authorities;

       authorities = roleSet.stream().map(role -> new SimpleGrantedAuthority("ROLE_"+role.getName().toUpperCase())).collect(Collectors.toList());

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
    return user.getEmail();
    }

    public String getFullName() {return String.format("%s %s", user.getFirstName(), user.getLastName());}

    public String getProfilePicUrl(){
        return "/user-photos/"+user.getId()+"/"+user.getPhoto();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getEnabled();
    }
}
