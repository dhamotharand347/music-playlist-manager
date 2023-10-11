package com.xmplar.music.playlist.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "playlist_song")
public class PlaylistSong {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "playlist_song_id")
	private int playlist_song_id;

	@Column(name = "playlist_id")
	private int playlist_id;

	@Column(name = "song_id")
	private int song_id;

	public int getPlaylist_song_id() {
		return playlist_song_id;
	}

	public void setPlaylist_song_id(int playlist_song_id) {
		this.playlist_song_id = playlist_song_id;
	}

	public int getPlaylist_id() {
		return playlist_id;
	}

	public void setPlaylist_id(int playlist_id) {
		this.playlist_id = playlist_id;
	}

	public int getSong_id() {
		return song_id;
	}

	public void setSong_id(int song_id) {
		this.song_id = song_id;
	}

}
