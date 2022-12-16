package cu.edu.cujae.pweb.service;

import cu.edu.cujae.pweb.dto.UserDto;

import java.util.List;

public interface UserService {
	List<UserDto> getUsers();
	UserDto getUserById(String userId);
	UserDto getUserByUsername(String username);
	void createUser(UserDto user);
	void registerUser(UserDto user);
	void updateUser(UserDto user);
	void deleteUser(String id);
	Integer getUsersSize();
}