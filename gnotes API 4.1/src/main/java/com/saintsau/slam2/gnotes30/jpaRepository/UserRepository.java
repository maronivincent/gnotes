package com.saintsau.slam2.gnotes30.jpaRepository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.saintsau.slam2.gnotes30.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
	User findById(long id);
	User findByUsername(String username);

	
}
