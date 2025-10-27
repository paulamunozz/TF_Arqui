package com.upc.ecocycle.security.repositories;

import com.upc.ecocycle.security.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
