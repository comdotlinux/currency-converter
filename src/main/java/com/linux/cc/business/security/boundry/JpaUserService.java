package com.linux.cc.business.security.boundry;

import com.linux.cc.business.security.control.RoleRepository;
import com.linux.cc.business.security.control.UserRepository;
import com.linux.cc.business.security.entity.Role;
import com.linux.cc.business.security.entity.User;
import static java.util.Arrays.asList;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class JpaUserService implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<>(asList(userRole)));
        userRepository.save(user);
    }

}
