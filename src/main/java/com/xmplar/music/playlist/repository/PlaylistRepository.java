package com.xmplar.music.playlist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.xmplar.music.playlist.model.Playlist;

public interface PlaylistRepository extends JpaRepository<Playlist,Long>{

	@Query(value = "select count(user_id) from playlist where user_id = :user_id", nativeQuery = true)
	int countUserId(@Param("user_id") int user_id);
	
	@Query(value = "select playlist_id from playlist where playlist_name = :playlist_name", nativeQuery = true)
	int findPlaylistIdByPlaylistName(@Param("playlist_name") String playlist_name);
	
	boolean existsByPlaylistName(String playlistName);

	@Query(value = "select * from playlist where playlist_name = :playlist_name", nativeQuery = true)
	Playlist findPlaylistByPlaylistName(@Param("playlist_name") String playlist_name);
}
