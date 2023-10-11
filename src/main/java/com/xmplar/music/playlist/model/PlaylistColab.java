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
	private int playlistColabId;

	@Column(name = "playlist_id")
	private int playlistId;

	@Column(name = "user_id")
	private int userId;

	public int getPlaylistColabId() {
		return playlistColabId;
	}

	public void setPlaylistColabId(int playlistColabId) {
		this.playlistColabId = playlistColabId;
	}

	public int getPlaylistId() {
		return playlistId;
	}

	public void setPlaylistId(int playlistId) {
		this.playlistId = playlistId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}
