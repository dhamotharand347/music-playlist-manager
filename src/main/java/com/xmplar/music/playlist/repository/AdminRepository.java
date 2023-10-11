package com.xmplar.music.playlist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.xmplar.music.playlist.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {

}
