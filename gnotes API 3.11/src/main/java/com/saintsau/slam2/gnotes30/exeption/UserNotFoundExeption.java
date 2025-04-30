package com.saintsau.slam2.gnotes30.exeption;

public class UserNotFoundExeption extends RuntimeException  {
	
	public UserNotFoundExeption(Long id) {
		super ("Could not find User with id : " + id);
        
    }

}
