package com.shopme.admin.user.repository;

import com.shopme.common.entity.Role;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Propagation;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
@DataJpaTest
@EntityScan({"com.shopme.common", "com.shopme.admin.user"})
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Transactional
@Rollback(value = false)
class RoleRepositoryTest {

    @Autowired
    RoleRepository repository;

    @Autowired
    TestEntityManager entityManager;

//    @Test
    public void createAdminRole(){
        Role roleAdmin = Role.builder().name("Admin").description("manage everything").build();

        Role roleSalesperson = new Role("Salesperson", "manage product price, "
                + "customers, shipping, orders and sales report");

        Role roleEditor = new Role("Editor", "manage categories, brands, "
                + "products, articles and menus");

        Role roleShipper = new Role("Shipper", "view products, view orders "
                + "and update order status");

        Role roleAssistant = new Role("Assistant", "manage questions and reviews");

        repository.saveAll(List.of(roleAdmin, roleSalesperson, roleEditor, roleShipper, roleAssistant));

    }

    @Test
    public void finAllRoles(){
        List<Role> roleList = (List<Role>) repository.findAll();
        roleList.forEach(role -> System.out.println(role.toString()));
    }
}