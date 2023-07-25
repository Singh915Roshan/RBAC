package com.crud.userOperation.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.crud.userOperation.entity.Role;
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name); 

}
