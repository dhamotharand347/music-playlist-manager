package com.xmplar.music.playlist.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "liked_song")
public class LikedSongs {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "liked_song_id")
	private int liked_song_id;

	@Column(name = "song_id")
	private int song_id;

	@Column(name = "user_id")
	private int user_id;

	public int getLiked_song_id() {
		return liked_song_id;
	}

	public void setLiked_song_id(int liked_song_id) {
		this.liked_song_id = liked_song_id;
	}

	public int getSong_id() {
		return song_id;
	}

	public void setSong_id(int song_id) {
		this.song_id = song_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

}
