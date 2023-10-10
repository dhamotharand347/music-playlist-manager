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
	@Column(name = "playlistSongId")
	private int playlistSongId;
	
	@Column(name = "playlistId")
	private int playlistId;

	@Column(name = "songId")
	private int songId;

	public int getPlaylistSongId() {
		return playlistSongId;
	}

	public void setPlaylistSongId(int playlistSongId) {
		this.playlistSongId = playlistSongId;
	}

	public int getPlaylistId() {
		return playlistId;
	}

	public void setPlaylistId(int playlistId) {
		this.playlistId = playlistId;
	}

	public int getSongId() {
		return songId;
	}

	public void setSongId(int songId) {
		this.songId = songId;
	}
	
	
}
