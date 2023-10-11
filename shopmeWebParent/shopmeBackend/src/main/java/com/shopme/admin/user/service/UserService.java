package com.shopme.admin.user.service;

import com.shopme.admin.user.repository.RoleRepository;
import com.shopme.admin.user.repository.UserRepository;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort.Direction;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    public  final int PAGE_SIZE=4;

    @Autowired
    UserRepository repository;

    @Autowired
    RoleRepository roleRepository;

    public List<User> findAllUsers(){
        return repository.findAll();
    }

    public Page<User> findAllUsersByPage(int pageNum){
        Pageable pageable = PageRequest.of(pageNum-1, PAGE_SIZE);
           Page<User> page =  repository.findAll(pageable);
           return page;
    }

    public Page<User> findAllUsersByPage(int pageNum, String sortColumn, String sortOrder, String keyword){
        Sort sort = Sort.by( sortOrder.equalsIgnoreCase("desc") ? Direction.DESC : Direction.ASC, sortColumn);
        Pageable pageable = PageRequest.of(pageNum-1, PAGE_SIZE).withSort(sort);
        if(!Objects.isNull(keyword) && !keyword.isBlank() && !keyword.toLowerCase().equals("null")){
            System.out.println("Service :: keyword not null :: "+keyword);
            return repository.findUserByKeyword(keyword, pageable);
        }
        return repository.findAll(pageable);
    }

    public List<Role> findAllRoles(){
        return roleRepository.findAll();
    }

    public User saveUser(User user){
        return repository.save(user);
    }

    public User findUserById(int id){
        return repository.findById(id).get();
    }

    public void deleteUser(User user){
        repository.delete(user);
    }

}
