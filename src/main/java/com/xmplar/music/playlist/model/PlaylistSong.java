package com.xmplar.music.playlist.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "playlistsong")
public class PlaylistSong {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sl_no")
	private int sl_no;
	
	@Column(name = "playlist_id")
	private int playlist_id;

	@Column(name = "song_no")
	private int song_no;

	@Column(name = "song_order")
	private int song_order;

	public int getPlaylist_id() {
		return playlist_id;
	}

	public void setPlaylist_id(int playlist_id) {
		this.playlist_id = playlist_id;
	}

	public int getSong_no() {
		return song_no;
	}

	public void setSong_no(int song_no) {
		this.song_no = song_no;
	}

	public int getSong_order() {
		return song_order;
	}

	public void setSong_order(int song_order) {
		this.song_order = song_order;
	}
	
}
