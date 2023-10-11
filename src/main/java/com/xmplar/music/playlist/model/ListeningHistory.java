package com.xmplar.music.playlist.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "listening_history")
public class ListeningHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "listening_history_id")
	private int listening_history_id;

	@Column(name = "user_id")
	private int user_id;

	@Column(name = "song_id")
	private int song_id;

	public int getListening_history_id() {
		return listening_history_id;
	}

	public void setListening_history_id(int listening_history_id) {
		this.listening_history_id = listening_history_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getSong_id() {
		return song_id;
	}

	public void setSong_id(int song_id) {
		this.song_id = song_id;
	}

}
