package com.multishop.fusiontech.repositories;

import com.multishop.fusiontech.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String roleUser);
}