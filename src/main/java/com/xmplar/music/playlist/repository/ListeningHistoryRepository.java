package com.xmplar.music.playlist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.xmplar.music.playlist.model.ListeningHistory;

public interface ListeningHistoryRepository extends JpaRepository<ListeningHistory, Long>{
	
	@Query(value = "select case when exists(select 1 from listening_history where user_id = :userId and song_id = :songId) then cast(1 as bit) else cast(0 as bit) end", nativeQuery = true)
	boolean existsByUserIdAndSongId(@Param("userId") int userId, @Param("songId") int songId);

}
