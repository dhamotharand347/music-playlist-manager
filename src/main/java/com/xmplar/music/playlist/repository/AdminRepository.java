package com.xmplar.music.playlist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.xmplar.music.playlist.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
	
	@Query(value = "SELECT password FROM admin where adminname = :adminname", nativeQuery = true)
	String findPassword(@Param("adminname")String adminname);
	
	boolean existsByAdminname(String adminname);

}
