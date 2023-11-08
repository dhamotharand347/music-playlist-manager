package com.xmplar.music.playlist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.xmplar.music.playlist.model.PlaylistSong;
import com.xmplar.music.playlist.model.Song;

public interface PlaylistSongRepository extends JpaRepository<PlaylistSong,Long>{
	
	@Query(value = "select count(song_id) from playlist_song where playlist_id = :playlist_id", nativeQuery = true)
	int countSongPerPlaylist(@Param("playlist_id") int playlist_id);
	
	//marked for review
	@Query(value = "select song_id from playlist_song where playlist_id = :playlistId", nativeQuery = true)
	Song findSongByPlaylistId(@Param("playlistId") String playlistId);
	
	@Query(value = "select case when exists(select * from playlist_song where playlist_id = :playlistId) then cast(1 as bit) else cast(0 as bit) end", nativeQuery= true)
	boolean existsByPlaylistId(@Param("playlistId") int playlistId);
	
	@Query(value = "select case when exists(select 1 from playlist_song where playlist_id = :playlistId and song_id = :songId) then cast(1 as bit) else cast(0 as bit) end", nativeQuery = true)
	boolean existsByPlaylistIdAndSongId(@Param("playlistId") int playlistId, @Param("songId") int songId);

	@Query(value = "delete from playlist_song where playlist_id = :playlistId and song_id = :songId", nativeQuery = true)
	void deleteSongFromPlaylist(@Param("playlistId")int playlistId, @Param("songId") int songId);
}