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
@Table(name = "playlist_song")
public class PlaylistSong {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "playlist_song_id")
	private int playlistSongId;

	@ManyToOne
	@JoinColumn(name = "playlist_id")
	private Playlist playlist;

	@ManyToOne
	@JoinColumn(name = "song_id")
	private Song song;

	public int getPlaylistSongId() {
		return playlistSongId;
	}

	public void setPlaylistSongId(int playlistSongId) {
		this.playlistSongId = playlistSongId;
	}

	public Playlist getPlaylist() {
		return playlist;
	}

	public void setPlaylist(Playlist playlist) {
		this.playlist = playlist;
	}

	public Song getSong() {
		return song;
	}

	public void setSong(Song song) {
		this.song = song;
	}
}
