package com.upc.ecocycle.security.services;

import com.upc.ecocycle.security.entities.Role;
import com.upc.ecocycle.security.entities.User;
import com.upc.ecocycle.security.repositories.RoleRepository;
import com.upc.ecocycle.security.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public void saveUser(User user) {userRepository.save(user);}

    @Transactional
    public void saveRole(Role role) {roleRepository.save(role);}

    public Integer insertUserRole(Integer user_id, Integer role_id) {
        Integer result = 0;
        userRepository.insertUserRole(user_id,role_id);
        return 1;
    }
}
