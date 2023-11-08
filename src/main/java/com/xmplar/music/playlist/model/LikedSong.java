package com.xmplar.music.playlist.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "liked_song")
public class LikedSong {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "liked_song_id")
	private int likedSongId;

	@Column(name = "song_id")
	private int songId;

	@Column(name = "user_id")
	private int userId;

	public int getLikedSongId() {
		return likedSongId;
	}

	public void setLikedSongId(int likedSongId) {
		this.likedSongId = likedSongId;
	}

	public int getSongId() {
		return songId;
	}

	public void setSongId(int songId) {
		this.songId = songId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
