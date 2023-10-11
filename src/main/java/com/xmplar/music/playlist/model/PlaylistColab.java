package com.xmplar.music.playlist.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "playlist_colab")
public class PlaylistColab {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "playlist_colab_id")
	private int playlist_colab_id;

	@Column(name = "playlist_id")
	private int playlist_id;

	@Column(name = "user_id")
	private int user_id;

	public int getPlaylist_colab_id() {
		return playlist_colab_id;
	}

	public void setPlaylist_colab_id(int playlist_colab_id) {
		this.playlist_colab_id = playlist_colab_id;
	}

	public int getPlaylist_id() {
		return playlist_id;
	}

	public void setPlaylist_id(int playlist_id) {
		this.playlist_id = playlist_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

}
