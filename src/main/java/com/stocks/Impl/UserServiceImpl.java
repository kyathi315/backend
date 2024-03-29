package com.stocks.Impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stocks.DTO.UserDTO;
import com.stocks.Exceptions.UserException;
import com.stocks.Mapper.UserMapper;
import com.stocks.Repository.UserRepository;
import com.stocks.service.UserService;
import com.stocks.user.User;

@Service
public class UserServiceImpl implements UserService{
	
	 @Autowired
	 UserRepository userRepository;


	 @Override
	 public UserDTO registerUser(UserDTO userDTO) {
	     if (userRepository.existsByUsername(userDTO.getUsername())) {
	         throw new UserException("User already exists with username: " + userDTO.getUsername());
	     }
	     
	     if (userRepository.existsByEmail(userDTO.getEmail())) {
	         throw new UserException("User already exists with email: " + userDTO.getEmail());
	     }
	     if (userDTO.getPassword() == null || userDTO.getPassword().trim().isEmpty()) {
	         throw new UserException("Password is required");
	     }

	     User user = UserMapper.toEntity(userDTO);
	     user = userRepository.save(user);
	     return UserMapper.toDTO(user);
	 }


	 @Override
	 public boolean loginUser(String username, String password) {
	     User user = userRepository.findByUsername(username);
	     if (user == null) {
	         throw new UserException("Invalid username.");
	     }
	     if (!user.getPassword().equals(password)) {
	         throw new UserException("Incorrect password.");
	     }
	     return true;
	 }

}
