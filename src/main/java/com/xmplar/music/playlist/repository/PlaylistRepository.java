package com.xmplar.music.playlist.repository;

import java.util.List;

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
	
	@Query(value = "select * from playlist where visibility = 'public'", nativeQuery = true)
	List<Playlist> findPlaylistByVisibilityPublic();
	
	@Query(value = "select total_duration from playlist where playlist_name = :playlist_name", nativeQuery = true)
	String findTotalDurationByPlaylistName(@Param("playlist_name") String playlist_name);
	
	@Query(value = "select visibility from playlist where playlist_name = :playlist_name", nativeQuery = true)
	String findVisibilityByPlaylistName(@Param("playlist_name") String playlist_name);
	
	@Query(value = "select case when exists(select 1 from playlist where playlist_name = :playlistName and user_id = :userId) then cast(1 as bit) else cast(0 as bit) end", nativeQuery = true)
	boolean existsByPlaylistNameAndUserId(@Param("playlistName") String playlistName, @Param("userId") int userId);

	@Query(value = "select case when exists(select 1 from playlist where playlist_id = :playlistId and user_id = :userId) then cast(1 as bit) else cast(0 as bit) end", nativeQuery = true)
	boolean existsByPlaylistIdAndUserId(@Param("playlistId") int playlistId, @Param("userId") int userId);
	
}
