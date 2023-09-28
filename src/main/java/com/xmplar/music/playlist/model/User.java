package com.xmplar.music.playlist.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "music_user")
public class User {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sl_no")
	private int sl_no;

	@Id
	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "prefered_lang")
	private String prefered_lang;
	
	@Column(name = "prefered_genre")
	private String prefered_genre;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPrefered_lang() {
		return prefered_lang;
	}

	public void setPrefered_lang(String prefered_lang) {
		this.prefered_lang = prefered_lang;
	}

	public String getPrefered_genre() {
		return prefered_genre;
	}

	public void setPrefered_genre(String prefered_genre) {
		this.prefered_genre = prefered_genre;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}