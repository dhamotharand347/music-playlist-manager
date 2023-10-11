package com.xmplar.music.playlist.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "playlist")
public class Playlist {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "playlist_id")
	private int playlist_id;

	@Column(name = "user_id")
	private int user_id;

	@Column(name = "playlist_name")
	private String playlist_name;

	@Column(name = "total_duration")
	private String total_duration;

	@Column(name = "visibility")
	private String visibility;

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

	public String getPlaylist_name() {
		return playlist_name;
	}

	public void setPlaylist_name(String playlist_name) {
		this.playlist_name = playlist_name;
	}

	public String getTotal_duration() {
		return total_duration;
	}

	public void setTotal_duration(String total_duration) {
		this.total_duration = total_duration;
	}

	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

}
