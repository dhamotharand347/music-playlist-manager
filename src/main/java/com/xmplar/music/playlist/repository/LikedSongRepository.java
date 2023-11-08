package com.xmplar.music.playlist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.xmplar.music.playlist.model.LikedSong;

public interface LikedSongRepository extends JpaRepository<LikedSong, Long> {
	
	@Query(value = "select case when exists(select 1 from liked_song where song_id = :songId and user_id = :userId) then cast(1 as bit) else cast(0 as bit) end", nativeQuery = true)
	boolean existsBySongIdAndUserId(@Param("songId") int songId, @Param("userId") int userId);

}
