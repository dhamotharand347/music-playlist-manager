package com.xmplar.music.playlist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.xmplar.music.playlist.model.PlaylistColab;

public interface PlaylistColabRepository extends JpaRepository<PlaylistColab,Long>{

	@Query(value = "select case when exists(select 1 from playlist_colab where playlist_id = :playlistId and user_id = :userId) then cast(1 as bit) else cast(0 as bit) end", nativeQuery = true)
	boolean existsByPlaylistIdAndUserId(@Param("playlistId") int playlistId, @Param("userId") int userId);
}
