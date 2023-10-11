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
	private int playlistSongId;

	@Column(name = "playlist_id")
	private int playlistId;

	@Column(name = "song_id")
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
