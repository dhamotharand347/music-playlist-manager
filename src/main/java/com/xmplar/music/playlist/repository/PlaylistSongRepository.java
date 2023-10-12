package com.xmplar.music.playlist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.xmplar.music.playlist.model.PlaylistSong;

public interface PlaylistSongRepository extends JpaRepository<PlaylistSong,Long>{
	
	@Query(value = "select count(song_id) from playlist_song where playlist_id = :playlist_id", nativeQuery = true)
	int countSongPerPlaylist(@Param("playlist_id") int playlist_id);

}
