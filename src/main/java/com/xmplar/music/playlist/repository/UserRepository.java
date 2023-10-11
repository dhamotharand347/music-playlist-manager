package com.xmplar.music.playlist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.xmplar.music.playlist.model.User;

public interface UserRepository extends JpaRepository<User,Long>{
	
	@Query(value = "SELECT password FROM music_user where username = :username", nativeQuery = true)
	String findPassword(@Param("username")String username);
	
	boolean existsByUsername(String username);

}
