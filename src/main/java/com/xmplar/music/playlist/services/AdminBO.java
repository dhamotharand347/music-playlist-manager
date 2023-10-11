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

		adminRepository.save(admin);
		return buildValidResponse(apiRequest, "Admin successfully registered!");
	}

	/**
	 * For admin login
	 */
	public ResponseEntity<APIEndpointResponse> adminLogin(APIRequest apiRequest) {

		// To do validation
		Admin admin = (Admin) GsonUtils.serializeObjectFromMap(apiRequest.getMandatoryParams(), Admin.class);

		String adminname = admin.getAdminname();
		String password = admin.getPassword();

		if (password.equals(adminRepository.findPassword(adminname))) {
			return buildValidResponse(apiRequest, "Admin logged in successfully!");
		}

		return buildValidResponse(apiRequest, "Admin log in failed!");
	}

	/**
	 * To add new songs in the database
	 */
	public ResponseEntity<APIEndpointResponse> addSong(APIRequest apiRequest) {

		// To do validation
		Song song = (Song) GsonUtils.serializeObjectFromMap(apiRequest.getMandatoryParams(), Song.class);

		songRepository.save(song);
		return buildValidResponse(apiRequest, "Song added successfully!");
	}

	/**
	 * To edit existing song in the database
	 */
	public ResponseEntity<APIEndpointResponse> editSong(APIRequest apiRequest) {
		// To do validation
		Song song = (Song) GsonUtils.serializeObjectFromMap(apiRequest.getMandatoryParams(), Song.class);

		String song_name = song.getSong_name();
		String singer = song.getSinger();
		String music_director = song.getMusic_director();
		String genre = song.getGenre();
		String language = song.getLanguage();

		Song existingSong = songRepository.findSongBySong_name(song_name);

		if (singer != null) {
			existingSong.setSinger(singer);
		} else if (music_director != null) {
			existingSong.setMusic_director(music_director);
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
		// To do validation\
		Song song = (Song) GsonUtils.serializeObjectFromMap(apiRequest.getMandatoryParams(), Song.class);

		String song_name = song.getSong_name();

		Song existingSong = songRepository.findSongBySong_name(song_name);

		try {
		songRepository.deleteBySongId(existingSong.getSong_id());
		} catch(Exception e) {
			return buildValidResponse(apiRequest, "Song was removed successfully!");
		}
		return buildValidResponse(apiRequest, "Song was removed successfully!");
	}
}
