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

		if (userRepository.existsByUsername(playlist.getUser().getUsername())) {

			if (playlistRepository.existsByPlaylistName(playlist.getPlaylistName())) {
				return buildErrorResponse(apiRequest, "Playlist already exists", "Playlist creation failed");
			}

			if (playlistRepository
					.countUserId(userRepository.findUserIdByUsername(playlist.getUser().getUsername())) == 10) {
				return buildErrorResponse(apiRequest, "Maximum count(10) to create playlist reached",
						"Playlist creation failed");
			}

			playlist.setUser(userRepository.findUserByUsername(playlist.getUser().getUsername()));

			playlistRepository.save(playlist);

			return buildValidResponse(apiRequest, "Playlist created successfully!");
		} else {

			return buildErrorResponse(apiRequest, "Username does not exists", "Playlist creation failed");
		}

	}

	public ResponseEntity<APIEndpointResponse> addToPlaylist(APIRequest apiRequest) {
		// To do validation
		/*
		 * 1) Attribute validation
		 */

		PlaylistSong playlistSong = GsonUtils.serializeObjectFromMap(apiRequest.getMandatoryParams(),
				PlaylistSong.class);

		if (playlistRepository.existsByPlaylistName(playlistSong.getPlaylist().getPlaylistName())) {
			if (songRepository.existsBySongName(playlistSong.getSong().getSongName())) {
				if(playlistSongRepository.countSongPerPlaylist(playlistSong.getPlaylist().getPlaylistId()) <= 10) {
					Playlist playlist = playlistRepository
							.findPlaylistByPlaylistName(playlistSong.getPlaylist().getPlaylistName());
					Song song = songRepository.findSongBySongName(playlistSong.getSong().getSongName());
					playlistSong.setPlaylist(playlist);
					playlistSong.setSong(song);
	
					playlistSongRepository.save(playlistSong);
	
					return buildValidResponse(apiRequest, "Song added to the playlist successfully!");
				} else {
					return buildErrorResponse(apiRequest, "Only 10 songs can be added to the playlist", "Song addition to playlist failed");
				}
			} else {
				return buildErrorResponse(apiRequest, "Song does not exists", "Song addition to playlist failed");
			}

		} else {
			return buildErrorResponse(apiRequest, "Playlist does not exists", "Song addition to playlist failed");
		}
	}
}
