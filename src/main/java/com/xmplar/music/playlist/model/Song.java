package com.xmplar.music.playlist.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "song")
public class Song {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "song_id")
	private int song_id;

	@Column(name = "song_name")
	private String song_name;

	@Column(name = "singer")
	private String singer;

	@Column(name = "music_director")
	private String music_director;

	@Column(name = "genre")
	private String genre;

	@Column(name = "year_of_release")
	private String year_of_release;

	@Column(name = "duration")
	private String duration;

	@Column(name = "language")
	private String language;

	public int getSong_id() {
		return song_id;
	}

	public void setSong_id(int song_id) {
		this.song_id = song_id;
	}

	public String getSong_name() {
		return song_name;
	}

	public void setSong_name(String song_name) {
		this.song_name = song_name;
	}

	public String getSinger() {
		return singer;
	}

	public void setSinger(String singer) {
		this.singer = singer;
	}

	public String getMusic_director() {
		return music_director;
	}

	public void setMusic_director(String music_director) {
		this.music_director = music_director;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getYear_of_release() {
		return year_of_release;
	}

	public void setYear_of_release(String year_of_release) {
		this.year_of_release = year_of_release;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

}
