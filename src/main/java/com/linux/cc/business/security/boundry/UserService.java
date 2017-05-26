package com.linux.cc.business.security.boundry;

import com.linux.cc.business.security.entity.User;


public interface UserService {
	public User findUserByEmail(String email);
	public void saveUser(User user);
}
