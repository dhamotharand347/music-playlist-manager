package com.xmplar.music.playlist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.xmplar.music.playlist.model.Song;

public interface SongRepository extends JpaRepository<Song, Long> {

	@Query(value="select * from song where song_name = :song_name",nativeQuery = true)
	Song findSongBySongName(@Param("song_name") String songName);
	
	@Query(value = "DELETE FROM song WHERE song_id = :song_id", nativeQuery = true)
	void deleteBySongId(@Param("song_id") int songId);

	boolean existsBySongName(String songName);
}
