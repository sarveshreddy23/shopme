package com.shopme.admin.user.repository;

import com.shopme.common.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.firstName like %?1% OR lastName like %?1% or email like %?1%")
    public Page<User> findUserByKeyword(String keyword, Pageable pageable );

//    @Query("SELECT u FROM User u WHERE u.email like %?1%")
public List<User> findByEmail(String email);
}
