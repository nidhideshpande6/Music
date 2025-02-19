package Nidhi.com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Nidhi.com.example.demo.model.User;
import Nidhi.com.example.demo.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public void registerUser(User user) {
		userRepository.save(user);
	}
	
	public Optional<User> findByemail(String email) {
		return Optional.ofNullable(userRepository.findByemail(email));
	}

	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}
}
