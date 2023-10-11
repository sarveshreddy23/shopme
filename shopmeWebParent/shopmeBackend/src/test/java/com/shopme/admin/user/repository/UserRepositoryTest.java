package com.shopme.admin.user.repository;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.Set;

@DataJpaTest
@EntityScan({"com.shopme.common", "com.shopme.admin.user"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback(value = false)
class UserRepositoryTest {

    @Autowired
    UserRepository repository;

    @Autowired
    TestEntityManager entityManager;
//    @Test
    public void createUser(){
        Role role = entityManager.find(Role.class, 1);
        User user = User.builder().email("test@gmail.com")
                .firstName("sarvesh")
                .lastName("reddy")
                .password("password")
                .enabled(true)
                .photo("myPic.png")
                .roles(Set.of(role)).build();
        repository.save(user);
    }

}