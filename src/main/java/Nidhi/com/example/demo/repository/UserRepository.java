package Nidhi.com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Nidhi.com.example.demo.model.User;

public interface UserRepository extends JpaRepository<User,Integer> {

	User findByemail(String email);

}
