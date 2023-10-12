package com.xmplar.music.playlist.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "playlist")
public class Playlist {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "playlist_id")
	private int playlistId;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "playlist_name")
	private String playlistName;

	@Column(name = "total_duration")
	private String totalDuration;

	@Column(name = "visibility")
	private String visibility;
	
	public int getPlaylistId() {
		return playlistId;
	}

	public void setPlaylistId(int playlistId) {
		this.playlistId = playlistId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPlaylistName() {
		return playlistName;
	}

	public void setPlaylistName(String playlistName) {
		this.playlistName = playlistName;
	}

	public String getTotalDuration() {
		return totalDuration;
	}

	public void setTotalDuration(String totalDuration) {
		this.totalDuration = totalDuration;
	}

	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	@Override
	public String toString() {
		return "Playlist [user=" + user.getUsername() + "]";
	}
}
