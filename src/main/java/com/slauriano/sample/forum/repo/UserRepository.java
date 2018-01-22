package com.slauriano.sample.forum.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.slauriano.sample.forum.model.User;

public interface UserRepository extends JpaRepository<User,Long> {
	
	//User findOne(String userName,String password);
	User findByUserNameAndPassword(String userName,String password);
	User findById(Long id);

}
