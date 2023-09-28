package com.xmplar.music.playlist.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "liked_songs")
public class LikedSongs {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sl_no")
	private int sl_no;

	@Column(name = "song_no")
	private int song_no;

	@Column(name = "username")
	private String username;

	public int getSong_no() {
		return song_no;
	}

	public void setSong_no(int song_no) {
		this.song_no = song_no;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
