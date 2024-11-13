package com.multishop.fusiontech.repositories;

import com.multishop.fusiontech.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    static Role findByName(String roleUser) {
        Role role = new Role();
        role.setName(roleUser);
        return role;
    }
}