package com.xmplar.music.playlist.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.xmplar.music.playlist.dto.APIEndpointResponse;
import com.xmplar.music.playlist.dto.APIRequest;
import com.xmplar.music.playlist.model.Admin;
import com.xmplar.music.playlist.model.Song;
import com.xmplar.music.playlist.repository.AdminRepository;
import com.xmplar.music.playlist.repository.SongRepository;
import com.xmplar.music.playlist.util.GsonUtils;

@Service
public class AdminBO extends BaseBO {

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private SongRepository songRepository;

	/**
	 * To register new admin details
	 */
	public ResponseEntity<APIEndpointResponse> adminRegister(APIRequest apiRequest) {

		// To do Validation
		Admin admin = (Admin) GsonUtils.serializeObjectFromMap(apiRequest.getMandatoryParams(), Admin.class);

		if (adminRepository.existsByAdminname(admin.getAdminname())) {
			return buildErrorResponse(apiRequest, "Admin name already exists", "Registration failed");
		} else {
			adminRepository.save(admin);
			return buildValidResponse(apiRequest, "Admin successfully registered!");
		}
	}

	/**
	 * For admin login
	 */
	public ResponseEntity<APIEndpointResponse> adminLogin(APIRequest apiRequest) {

		// Client side validation
		/*
		 * 1) Username or password empty
		 */
		Admin admin = (Admin) GsonUtils.serializeObjectFromMap(apiRequest.getMandatoryParams(), Admin.class);

		String adminname = admin.getAdminname();
		String password = admin.getPassword();

		if (adminRepository.existsByAdminname(adminname)) {
			if (password.equals(adminRepository.findPassword(adminname))) {
				return buildValidResponse(apiRequest, "Admin logged in successfully!");
			} else {
				return buildErrorResponse(apiRequest, "Password does not match", "Log in failed");
			}

		} else {
			return buildErrorResponse(apiRequest, "Admin name does not exists", "Log in failed");
		}
	}

	/**
	 * To add new songs in the database
	 */
	public ResponseEntity<APIEndpointResponse> addSong(APIRequest apiRequest) {

		// To do validation
		/*
		 * 1) Valid song attributes 2) Attribute cannot be empty
		 */
		Song song = (Song) GsonUtils.serializeObjectFromMap(apiRequest.getMandatoryParams(), Song.class);

		if (songRepository.existsBySongName(song.getSongName())) {
			return buildErrorResponse(apiRequest, "Song name already exists", "Song addition failed");
		} else {
			songRepository.save(song);
			return buildValidResponse(apiRequest, "Song added successfully!");
		}
	}

	/**
	 * To edit existing song in the database
	 */
	public ResponseEntity<APIEndpointResponse> editSong(APIRequest apiRequest) {
		// To do validation
		/*
		 * 1) Missing song name 2) Missing edit attribute
		 */
		Song song = (Song) GsonUtils.serializeObjectFromMap(apiRequest.getMandatoryParams(), Song.class);

		String songName = song.getSongName();
		String singer = song.getSinger();
		String musicDirector = song.getMusicDirector();
		String genre = song.getGenre();
		String language = song.getLanguage();

		if (!songRepository.existsBySongName(song.getSongName())) {
			return buildErrorResponse(apiRequest, "Song does not found", "Song attribute updation failed");
		}

		Song existingSong = songRepository.findSongBySongName(songName);

		if (singer != null) {
			existingSong.setSinger(singer);
		} else if (musicDirector != null) {
			existingSong.setMusicDirector(musicDirector);
		} else if (genre != null) {
			existingSong.setGenre(genre);
		} else if (language != null) {
			existingSong.setLanguage(language);
		}

		songRepository.save(existingSong);

		return buildValidResponse(apiRequest, "Song attribute updated successfully!");
	}

	/**
	 * To remove existing song in the database
	 */
	public ResponseEntity<APIEndpointResponse> removeSong(APIRequest apiRequest) {
		// To do validation
		Song song = (Song) GsonUtils.serializeObjectFromMap(apiRequest.getMandatoryParams(), Song.class);
		
		if(!songRepository.existsBySongName(song.getSongName())) {
			return buildErrorResponse(apiRequest, "Song does not found", "Song removal failed");
		}
		
		String song_name = song.getSongName();

		Song existingSong = songRepository.findSongBySongName(song_name);

		try {
			songRepository.deleteBySongId(existingSong.getSongId());
		} catch (Exception e) {
			return buildValidResponse(apiRequest, "Song was removed successfully!");
		}
		return buildValidResponse(apiRequest, "Song was removed successfully!");
	}
}
