package com.xmplar.music.playlist.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.xmplar.music.playlist.dto.APIEndpointResponse;
import com.xmplar.music.playlist.dto.APIRequest;
import com.xmplar.music.playlist.model.Playlist;
import com.xmplar.music.playlist.model.PlaylistSong;
import com.xmplar.music.playlist.model.Song;
import com.xmplar.music.playlist.model.User;
import com.xmplar.music.playlist.repository.PlaylistRepository;
import com.xmplar.music.playlist.repository.PlaylistSongRepository;
import com.xmplar.music.playlist.repository.SongRepository;
import com.xmplar.music.playlist.repository.UserRepository;
import com.xmplar.music.playlist.util.GsonUtils;
import com.xmplar.music.playlist.validation.CommonUtils;

@Service
public class UserBO extends BaseBO {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PlaylistRepository playlistRepository;

	@Autowired
	private PlaylistSongRepository playlistSongRepository;

	@Autowired
	private SongRepository songRepository;

	/**
	 * To register new user details
	 */
	public ResponseEntity<APIEndpointResponse> userRegister(APIRequest apiRequest) {

		// To do Validation
		User user = (User) GsonUtils.serializeObjectFromMap(apiRequest.getMandatoryParams(), User.class);

		if (userRepository.existsByUsername(user.getUsername())) {
			return buildErrorResponse(apiRequest, "Username already exists", "Registration failed");
		} else {
			userRepository.save(user);
			return buildValidResponse(apiRequest, "User successfully registered!");
		}
	}

	/**
	 * For user login
	 */
	public ResponseEntity<APIEndpointResponse> userLogin(APIRequest apiRequest) {

		// Client side validation
		/*
		 * 1) Username or password empty
		 */
		User user = (User) GsonUtils.serializeObjectFromMap(apiRequest.getMandatoryParams(), User.class);

		String username = user.getUsername();
		String password = user.getPassword();

		if (userRepository.existsByUsername(username)) {
			if (password.equals(userRepository.findPassword(username))) {
				return buildValidResponse(apiRequest, "User logged in successfully!");
			} else {
				return buildErrorResponse(apiRequest, "Password does not match", "Log in failed");
			}

		} else {
			return buildErrorResponse(apiRequest, "Username does not exists", "Log in failed");
		}
	}

	public ResponseEntity<APIEndpointResponse> createPlaylist(APIRequest apiRequest) {
		// To do validation
		/*
		 * 1) visibility only be 'public' or 'private' 2) playlist attribute validation
		 * 3) check for empty attribute field
		 */

		Playlist playlist = GsonUtils.serializeObjectFromMap(apiRequest.getMandatoryParams(), Playlist.class);

		if (!userRepository.existsByUsername(playlist.getUser().getUsername())) {
			return buildErrorResponse(apiRequest, "Username does not exists", "Playlist creation failed");
		}

		if (playlistRepository.existsByPlaylistName(playlist.getPlaylistName())) {
			return buildErrorResponse(apiRequest, "Playlist already exists", "Playlist creation failed");
		}

		if (playlistRepository
				.countUserId(userRepository.findUserIdByUsername(playlist.getUser().getUsername())) == 10) {
			return buildErrorResponse(apiRequest, "Maximum count(10) to create playlist reached",
					"Playlist creation failed");
		}

		playlist.setUser(userRepository.findUserByUsername(playlist.getUser().getUsername()));
		playlist.setTotalDuration("00:00");

		playlistRepository.save(playlist);

		return buildValidResponse(apiRequest, "Playlist created successfully!");

	}

	public ResponseEntity<APIEndpointResponse> addToPlaylist(APIRequest apiRequest) {
		// To do validation
		/*
		 * 1) Attribute validation
		 */

		PlaylistSong playlistSong = GsonUtils.serializeObjectFromMap(apiRequest.getMandatoryParams(),
				PlaylistSong.class);

		if (!playlistRepository.existsByPlaylistName(playlistSong.getPlaylist().getPlaylistName())) {
			return buildErrorResponse(apiRequest, "Playlist does not exists", "Song addition to playlist failed");
		}
		if (!songRepository.existsBySongName(playlistSong.getSong().getSongName())) {
			return buildErrorResponse(apiRequest, "Song does not exists", "Song addition to playlist failed");
		}
		if (playlistSongRepository.countSongPerPlaylist(playlistSong.getPlaylist().getPlaylistId()) == 10) {
			return buildErrorResponse(apiRequest, "Only 10 songs can be added to the playlist",
					"Song addition to playlist failed");
		}
		Playlist playlist = playlistRepository.findPlaylistByPlaylistName(playlistSong.getPlaylist().getPlaylistName());
		Song song = songRepository.findSongBySongName(playlistSong.getSong().getSongName());
		playlistSong.setPlaylist(playlist);
		playlistSong.setSong(song);
		
		String totalDuration = CommonUtils.calculateTotalDuration(playlist.getTotalDuration(), song.getDuration());
		playlist.setTotalDuration(totalDuration);

		playlistRepository.save(playlist);
		playlistSongRepository.save(playlistSong);

		return buildValidResponse(apiRequest, "Song added to the playlist successfully!");
	}

	public ResponseEntity<APIEndpointResponse> editPlaylist(APIRequest apiRequest) {
		// To Do Validation
		/*
		 * 1) Edit attribute validation 2) Check for missing attribute
		 */

		Playlist playlist = GsonUtils.serializeObjectFromMap(apiRequest.getMandatoryParams(), Playlist.class);
		Playlist playlistAdd = GsonUtils.serializeObjectFromMap(apiRequest.getAdditionalParams(), Playlist.class);

		if (!playlistRepository.existsByPlaylistName(playlist.getPlaylistName())) {
			return buildErrorResponse(apiRequest, "Playlist does not found", "Playlist attribute updation failed");
		}
		if (playlistRepository.existsByPlaylistName(playlistAdd.getPlaylistName())) {
			return buildErrorResponse(apiRequest, "Playlist name already exists", "Playlist attribute updation failed");
		}

		String playlistName = playlistAdd.getPlaylistName();
		String visibility = playlistAdd.getVisibility();

		Playlist existingPlaylist = playlistRepository.findPlaylistByPlaylistName(playlist.getPlaylistName());

		if (visibility != null) {
			existingPlaylist.setVisibility(visibility);
		} else {
			existingPlaylist.setPlaylistName(playlistName);
		}

		playlistRepository.save(existingPlaylist);

		return buildValidResponse(apiRequest, "Playlist attribute updated successfully!");
	}
}
