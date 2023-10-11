package com.shopme.admin.user.service;

import com.shopme.admin.user.repository.RoleRepository;
import com.shopme.common.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    RoleRepository repository;

    public void addrole(){
        Role role = Role.builder().name("Admin").description("sample test").build();
        repository.save(role);
    }

}
