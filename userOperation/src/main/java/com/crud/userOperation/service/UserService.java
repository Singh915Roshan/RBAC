package com.crud.userOperation.service;

import java.util.List;
import java.util.Map;

import com.crud.userOperation.dto.UserDto;
import com.crud.userOperation.entity.User;
public interface UserService {
	 void saveUser(UserDto userDto);

	    User findUserByEmail(String email);

	    List<UserDto> findAllUsers();
	    void createUser(UserDto userDto);
	    UserDto getUserById(Long id);
	    void updateUser(Long id, UserDto userDto);
	    void updatePartialUser(Long id, Map<String, Object> updates);
	    void deleteUser(Long id);
}
