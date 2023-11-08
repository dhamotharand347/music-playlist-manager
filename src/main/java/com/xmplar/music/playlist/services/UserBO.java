package com.xmplar.music.playlist.services;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.xmplar.music.playlist.dto.APIContext;
import com.xmplar.music.playlist.dto.APIEndpointResponse;
import com.xmplar.music.playlist.dto.APIRequest;
import com.xmplar.music.playlist.model.LikedSong;
import com.xmplar.music.playlist.model.ListeningHistory;
import com.xmplar.music.playlist.model.Playlist;
import com.xmplar.music.playlist.model.PlaylistColab;
import com.xmplar.music.playlist.model.PlaylistSong;
import com.xmplar.music.playlist.model.Song;
import com.xmplar.music.playlist.model.User;
import com.xmplar.music.playlist.repository.LikedSongRepository;
import com.xmplar.music.playlist.repository.ListeningHistoryRepository;
import com.xmplar.music.playlist.repository.PlaylistColabRepository;
import com.xmplar.music.playlist.repository.PlaylistRepository;
import com.xmplar.music.playlist.repository.PlaylistSongRepository;
import com.xmplar.music.playlist.repository.SongRepository;
import com.xmplar.music.playlist.repository.UserRepository;
import com.xmplar.music.playlist.util.GsonUtils;
import com.xmplar.music.playlist.validation.CommonUtils;

@Service
public class UserBO extends BaseBO<Object> {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PlaylistRepository playlistRepository;
	@Autowired
	private PlaylistSongRepository playlistSongRepository;
	@Autowired
	private SongRepository songRepository;
	@Autowired
	private LikedSongRepository likedSongRepository;
	@Autowired
	private ListeningHistoryRepository listeningHistoryRepository;
	@Autowired
	private PlaylistColabRepository playlistColabRepository;

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

	public ResponseEntity<APIEndpointResponse> userLogin(APIRequest apiRequest) {
		// Client side validation -> Username or password empty
		User user = GsonUtils.serializeObjectFromMap(apiRequest.getMandatoryParams(), User.class);
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
		APIContext apiContext = apiRequest.getApiContext();

		if (playlistRepository.existsByPlaylistName(playlist.getPlaylistName())) {
			return buildErrorResponse(apiRequest, "Playlist already exists", "Playlist creation failed");
		}
		if (playlistRepository.countUserId(userRepository.findUserIdByUsername(apiContext.getUsername())) == 10) {
			return buildErrorResponse(apiRequest, "Maximum count(10) to create playlist reached",
					"Playlist creation failed");
		}
		playlist.setUser(userRepository.findUserByUsername(apiContext.getUsername()));
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
		APIContext apiContext = apiRequest.getApiContext();
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
		// Check if user has access to the given playlist via playlist or playlistColab
		int userId = userRepository.findUserIdByUsername(apiContext.getUsername());
		int playlistId = playlistSong.getPlaylist().getPlaylistId();
		if (!playlistColabRepository.existsByPlaylistIdAndUserId(playlistId, userId) && !playlistRepository
				.existsByPlaylistNameAndUserId(playlistSong.getPlaylist().getPlaylistName(), userId)) {
			return buildErrorResponse(apiRequest, "User has no access to the playlist",
					"Song addition to playlist failed");
		}
		// Check if the playlist already has the song
		int songId = playlistSong.getSong().getSongId();
		if (playlistSongRepository.existsByPlaylistIdAndSongId(playlistId, songId)) {
			return buildErrorResponse(apiRequest, "Song already exists", "Song already present in the given playlist");
		}
		Playlist playlist = playlistRepository.findPlaylistByPlaylistName(playlistSong.getPlaylist().getPlaylistName());
		Song song = songRepository.findSongBySongName(playlistSong.getSong().getSongName());
		playlistSong.setPlaylist(playlist);
		playlistSong.setSong(song);
		// Update the total duration of the playlist when new song was added
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
		APIContext apiContext = apiRequest.getApiContext();
		if (!playlistRepository.existsByPlaylistName(playlist.getPlaylistName())) {
			return buildErrorResponse(apiRequest, "Playlist does not found", "Playlist attribute updation failed");
		}
		if (playlistRepository.existsByPlaylistName(playlistAdd.getPlaylistName())) {
			return buildErrorResponse(apiRequest, "Playlist name already exists", "Playlist attribute updation failed");
		}
		// Check if user has access to the given playlist via playlist or playlistColab
		int userId = userRepository.findUserIdByUsername(apiContext.getUsername());
		int playlistId = playlistRepository.findPlaylistIdByPlaylistName(playlist.getPlaylistName());
		if (!playlistColabRepository.existsByPlaylistIdAndUserId(playlistId, userId)
				&& !playlistRepository.existsByPlaylistNameAndUserId(playlist.getPlaylistName(), userId)) {
			return buildErrorResponse(apiRequest, "User has no access to the playlist",
					"Song addition to playlist failed");
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

	public ResponseEntity<APIEndpointResponse> viewPlaylist(APIRequest apiRequest) {
		// To do validation
		Playlist playlist = GsonUtils.serializeObjectFromMap(apiRequest.getMandatoryParams(), Playlist.class);
		APIContext apiContext = apiRequest.getApiContext();
		if (!playlistRepository.existsByPlaylistName(playlist.getPlaylistName())) {
			return buildErrorResponse(apiRequest, "Playlist does not found", "View playlist failed");
		}
		// To check if the playlist belongs to the user and not 'public' and has access
		// via playlistColab
		int userId = userRepository.findUserIdByUsername(apiContext.getUsername());
		int playlistId = playlistRepository.findPlaylistIdByPlaylistName(playlist.getPlaylistName());
		if (!playlistRepository.existsByPlaylistIdAndUserId(playlistId, userId)
				&& !(playlistRepository.findVisibilityByPlaylistName(playlist.getPlaylistName())).equals("public")
				&& !playlistColabRepository.existsByPlaylistIdAndUserId(playlistId, userId)) {
			return buildErrorResponse(apiRequest, "Playlist not available", "Playlist not available for the user");
		}

		// Created "existingPlaylist", the variable "playlist" contains name only.
		Playlist existingPlaylist = playlistRepository.findPlaylistByPlaylistName(playlist.getPlaylistName());

		LinkedHashMap<String, Object> playlistDetails = new LinkedHashMap<>();
		playlistDetails.put("playlistName", existingPlaylist.getPlaylistName());
		playlistDetails.put("totalDuration", existingPlaylist.getTotalDuration());
		playlistDetails.put("visibility", existingPlaylist.getVisibility());

		return buildValidResponse(apiRequest, playlistDetails);
	}

	public ResponseEntity<APIEndpointResponse> viewPlaylistSong(APIRequest apiRequest) {
		// To do validation
		Playlist playlist = GsonUtils.serializeObjectFromMap(apiRequest.getMandatoryParams(), Playlist.class);
		APIContext apiContext = apiRequest.getApiContext();
		if (!playlistRepository.existsByPlaylistName(playlist.getPlaylistName())) {
			return buildErrorResponse(apiRequest, "Playlist does not found", "View playlist song failed");
		}

		// To check if the playlist belongs to the user and not 'public' and has access
		// via playlistColab
		int userId = userRepository.findUserIdByUsername(apiContext.getUsername());
		int playlistId = playlistRepository.findPlaylistIdByPlaylistName(playlist.getPlaylistName());
		if (!playlistRepository.existsByPlaylistIdAndUserId(playlistId, userId)
				&& !(playlistRepository.findVisibilityByPlaylistName(playlist.getPlaylistName())).equals("public")
				&& !playlistColabRepository.existsByPlaylistIdAndUserId(playlistId, userId)) {
			return buildErrorResponse(apiRequest, "Playlist not available", "Playlist not available for the user");
		}

		List<Song> playlistSongs = songRepository.findSongDetailsByPlaylistId(playlistId);

		if (playlistSongs.isEmpty()) {
			return buildValidResponse(apiRequest, "There are no songs in this playlist");
		}

		List<LinkedHashMap<String, Object>> songDetails = new ArrayList<>();
		for (Song song : playlistSongs) {
			LinkedHashMap<String, Object> songResult = new LinkedHashMap<>();
			songResult.put("songName", song.getSongName());
			songResult.put("singer", song.getSinger());
			songResult.put("genre", song.getGenre());
			songResult.put("duration", song.getDuration());
			songDetails.add(songResult);
		}
		return buildValidResponse(apiRequest, songDetails);
	}

	public ResponseEntity<APIEndpointResponse> removePlaylistSong(APIRequest apiRequest) {
		// To do validation
		PlaylistSong playlistSong = GsonUtils.serializeObjectFromMap(apiRequest.getMandatoryParams(),
				PlaylistSong.class);
		APIContext apiContext = apiRequest.getApiContext();

		int userId = userRepository.findUserIdByUsername(apiContext.getUsername());
		String playlistName = playlistSong.getPlaylist().getPlaylistName();

		if (!playlistRepository.existsByPlaylistName(playlistSong.getPlaylist().getPlaylistName())) {
			return buildErrorResponse(apiRequest, "Playlist does not exists", "Song removal failed");
		}
		if (!songRepository.existsBySongName(playlistSong.getSong().getSongName())) {
			return buildErrorResponse(apiRequest, "Song does not exists", "Song removal failed");
		}

		int songId = songRepository.findSongIdBySongName(playlistSong.getSong().getSongName());
		int playlistId = playlistRepository.findPlaylistIdByPlaylistName(playlistName);

		if (!playlistRepository.existsByPlaylistNameAndUserId(playlistName, userId)
				&& !playlistColabRepository.existsByPlaylistIdAndUserId(playlistId, userId)) {
			return buildErrorResponse(apiRequest, "User has no access", "User has no access to the given playlist");
		}
		if (!playlistSongRepository.existsByPlaylistIdAndSongId(playlistId, songId)) {
			return buildErrorResponse(apiRequest, "Song does not exists", "Song doesn't present in the given playlist");
		}
		// To update the total duration in playlist
		String existingDuration = playlistRepository.findTotalDurationByPlaylistName(playlistName);
		String songDuration = songRepository.findSongDurationBySongName(playlistSong.getSong().getSongName());
		String totalDuration = CommonUtils.subtractDurations(existingDuration, songDuration);
		Playlist playlist = playlistRepository.findPlaylistByPlaylistName(playlistName);
		playlist.setTotalDuration(totalDuration);
		playlistRepository.save(playlist);
		try {
			playlistSongRepository.deleteSongFromPlaylist(playlistId, songId);
		} catch (Exception e) {

		}
		return buildValidResponse(apiRequest, "Song was removed from the playlist successflly");
	}

	public ResponseEntity<APIEndpointResponse> searchSong(APIRequest apiRequest) {
		// To do console validation ->empty search parameter -> invalid search parameter
		Song songAttriute = GsonUtils.serializeObjectFromMap(apiRequest.getMandatoryParams(), Song.class);

		String songName = songAttriute.getSongName();
		String singer = songAttriute.getSinger();
		String musicDirector = songAttriute.getMusicDirector();
		String genre = songAttriute.getGenre();
		String language = songAttriute.getLanguage();

		List<Song> songDetails = null;
		if (songName != null) {
			songDetails = songRepository.findSongsBySongName(songName);
		} else if (singer != null) {
			songDetails = songRepository.findSongsBySinger(singer);
		} else if (musicDirector != null) {
			songDetails = songRepository.findSongsByMusicDirector(musicDirector);
		} else if (genre != null) {
			songDetails = songRepository.findSongsByGenre(genre);
		} else {
			songDetails = songRepository.findSongsByLanguage(language);
		}

		List<LinkedHashMap<String, Object>> songDetailsModified = new ArrayList<>();

		for (Song song : songDetails) {
			LinkedHashMap<String, Object> singleSong = new LinkedHashMap<>();
			singleSong.put("songName", song.getSongName());
			singleSong.put("singer", song.getSinger());
			singleSong.put("musicDirector", song.getMusicDirector());
			singleSong.put("genre", song.getGenre());
			singleSong.put("yearOfRelease", song.getYearOfRelease());
			singleSong.put("duration", song.getDuration());
			singleSong.put("language", song.getLanguage());
			songDetailsModified.add(singleSong);
		}

		return buildValidResponse(apiRequest, songDetailsModified);
	}

	public ResponseEntity<APIEndpointResponse> playSong(APIRequest apiRequest) {
		// To do validation
		Song song = GsonUtils.serializeObjectFromMap(apiRequest.getMandatoryParams(), Song.class);
		APIContext apiContext = apiRequest.getApiContext();

		String songName = song.getSongName();
		if (!songRepository.existsBySongName(songName)) {
			return buildErrorResponse(apiRequest, "Song does not exists", "Song doesn't present in the system");
		}

		// Display song details
		Song songDetails = songRepository.findSongBySongName(songName);
		LinkedHashMap<String, Object> singleSong = new LinkedHashMap<>();
		singleSong.put("songName", songDetails.getSongName());
		singleSong.put("singer", songDetails.getSinger());
		singleSong.put("genre", songDetails.getGenre());
		singleSong.put("duration", songDetails.getDuration());

		// To update listening history
		int userId = userRepository.findUserIdByUsername(apiContext.getUsername());
		int songId = songDetails.getSongId();

		// Not Update listening history table when the user already played that song
		if (!listeningHistoryRepository.existsByUserIdAndSongId(userId, songId)) {
			ListeningHistory listeningHistory = new ListeningHistory();
			listeningHistory.setSongId(songId);
			listeningHistory.setUserId(userId);
			listeningHistoryRepository.save(listeningHistory);
		}

		return buildValidResponse(apiRequest, singleSong);
	}

	public ResponseEntity<APIEndpointResponse> likeSong(APIRequest apiRequest) {
		// To do validation
		Song song = GsonUtils.serializeObjectFromMap(apiRequest.getMandatoryParams(), Song.class);
		APIContext apiContext = apiRequest.getApiContext();
		if (!songRepository.existsBySongName(song.getSongName())) {
			return buildErrorResponse(apiRequest, "Song does not exists", "Cannot add to the liked song playlist");
		}

		int userId = userRepository.findUserIdByUsername(apiContext.getUsername());
		int songId = songRepository.findSongIdBySongName(song.getSongName());

		if (likedSongRepository.existsBySongIdAndUserId(songId, userId)) {
			return buildErrorResponse(apiRequest, "Song already exists",
					"The song already exists in the liked song playlist");
		}

		LikedSong likedSong = new LikedSong();
		likedSong.setSongId(songId);
		likedSong.setUserId(userId);
		likedSongRepository.save(likedSong);

		return buildValidResponse(apiRequest, "Song was added to the liked song playlist successfully");
	}

	public ResponseEntity<APIEndpointResponse> viewLikedSong(APIRequest apiRequest) {
		// To do validation
		APIContext apiContext = apiRequest.getApiContext();

		int userId = userRepository.findUserIdByUsername(apiContext.getUsername());
		List<Song> likedSongs = songRepository.findLikedSongDetailsByUserId(userId);

		if (likedSongs.isEmpty()) {
			return buildValidResponse(apiRequest, "There are no liked songs yet!");
		}

		List<LinkedHashMap<String, Object>> songDetails = new ArrayList<>();
		for (Song song : likedSongs) {
			LinkedHashMap<String, Object> songResult = new LinkedHashMap<>();
			songResult.put("songName", song.getSongName());
			songResult.put("singer", song.getSinger());
			songResult.put("genre", song.getGenre());
			songResult.put("duration", song.getDuration());
			songDetails.add(songResult);
		}
		return buildValidResponse(apiRequest, songDetails);
	}

	public ResponseEntity<APIEndpointResponse> viewListeningHistory(APIRequest apiRequest) {
		// To do validation
		APIContext apiContext = apiRequest.getApiContext();
		int userId = userRepository.findUserIdByUsername(apiContext.getUsername());
		List<Song> listeningHistory = songRepository.findLikedSongDetailsByUserId(userId);

		if (listeningHistory.isEmpty()) {
			return buildValidResponse(apiRequest, "There are no played songs yet!");
		}

		List<LinkedHashMap<String, Object>> songDetails = new ArrayList<>();
		for (Song song : listeningHistory) {
			LinkedHashMap<String, Object> songResult = new LinkedHashMap<>();
			songResult.put("songName", song.getSongName());
			songResult.put("singer", song.getSinger());
			songResult.put("genre", song.getGenre());
			songResult.put("duration", song.getDuration());
			songDetails.add(songResult);
		}
		return buildValidResponse(apiRequest, songDetails);

	}

	public ResponseEntity<APIEndpointResponse> viewGlobalPlaylist(APIRequest apiRequest) {
		// To do validation

		List<Playlist> publicPlaylist = playlistRepository.findPlaylistByVisibilityPublic();

		if (publicPlaylist.isEmpty()) {
			return buildValidResponse(apiRequest, "There are public playlist available now!");
		}

		List<LinkedHashMap<String, Object>> playlistDetails = new ArrayList<>();
		for (Playlist playlist : publicPlaylist) {
			LinkedHashMap<String, Object> songResult = new LinkedHashMap<>();
			songResult.put("playlistName", playlist.getPlaylistName());
			songResult.put("totalDuration", playlist.getTotalDuration());
			songResult.put("username", playlist.getUser().getUsername());
			playlistDetails.add(songResult);
		}
		return buildValidResponse(apiRequest, playlistDetails);
	}

	public ResponseEntity<APIEndpointResponse> sharePlaylist(APIRequest apiRequest) {
		// To do validation
		Playlist playlist = GsonUtils.serializeObjectFromMap(apiRequest.getMandatoryParams(), Playlist.class);
		APIContext apiContext = apiRequest.getApiContext();
		int userId = userRepository.findUserIdByUsername(apiContext.getUsername());
		int sharedUserId = userRepository.findUserIdByUsername(playlist.getUser().getUsername());
		int playlistId = playlistRepository.findPlaylistIdByPlaylistName(playlist.getPlaylistName());

		if (!userRepository.existsByUsername(playlist.getUser().getUsername())) {
			return buildErrorResponse(apiRequest, "Username does not exists", "Cannot share playlist with this user");
		}
		if (!playlistRepository.existsByPlaylistNameAndUserId(playlist.getPlaylistName(), userId)) {
			return buildErrorResponse(apiRequest, "User has no access", "User has no access to the given playlist");
		}
		if (playlistColabRepository.existsByPlaylistIdAndUserId(playlistId, sharedUserId)) {
			return buildErrorResponse(apiRequest, "User already has access",
					"User already has access to the given playlist");
		}
		PlaylistColab playlistColab = new PlaylistColab();
		playlistColab.setPlaylistId(playlistId);
		playlistColab.setUserId(sharedUserId);
		playlistColabRepository.save(playlistColab);

		return buildValidResponse(apiRequest, "Playlist shared successfully!");
	}

}
