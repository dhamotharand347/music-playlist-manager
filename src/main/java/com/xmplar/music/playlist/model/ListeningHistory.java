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
	@Column(name = "listeningHistoryId")
	private int listeningHistoryId;
	
	@Column(name = "userId")
	private int userId;
	
	@Column(name = "songId")
	private int songId;

	public int getListeningHistoryId() {
		return listeningHistoryId;
	}

	public void setListeningHistoryId(int listeningHistoryId) {
		this.listeningHistoryId = listeningHistoryId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getSongId() {
		return songId;
	}

	public void setSongId(int songId) {
		this.songId = songId;
	}

	
}
