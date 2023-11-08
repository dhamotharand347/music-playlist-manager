package com.xmplar.music.playlist.repository;

import java.util.List;

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
	
	@Query(value = "select song_id from song where song_name = :song_name", nativeQuery = true)
	int findSongIdBySongName(@Param("song_name") String song_name);
	
	@Query(value = "select duration from song where song_name = :song_name", nativeQuery = true)
	String findSongDurationBySongName(@Param("song_name") String song_name);
	
	//Display playlist songs
	@Query(value = "select s.* from song s inner join playlist_song ps on ps.song_id = s.song_id where ps.playlist_id = :playlistId", nativeQuery = true)
	List<Song> findSongDetailsByPlaylistId(@Param("playlistId") int playlistId);
	
	//Display Liked songs
	@Query(value = "select s.* from song s inner join listening_history lh on lh.song_id = s.song_id where lh.user_id = :userId", nativeQuery = true)
	List<Song> findLikedSongDetailsByUserId(@Param("userId") int userId);
	
	//Display Listening History
	//Display Liked songs
	@Query(value = "select s.* from song s inner join liked_song ls on ls.song_id = s.song_id where ls.user_id = :userId", nativeQuery = true)
	List<Song> findListenedSongDetailsByUserId(@Param("userId") int userId);
	
	
	//Below methods for song search
	
	@Query(value="select * from song where song_name = :song_name",nativeQuery = true)
	List<Song> findSongsBySongName(@Param("song_name") String songName);
	
	@Query(value = "select * from song where singer = :singer", nativeQuery = true)
	List<Song> findSongsBySinger(@Param("singer") String singer);
	
	@Query(value = "select * from song where music_director = :musicDirector", nativeQuery = true)
	List<Song> findSongsByMusicDirector(@Param("musicDirector") String musicDirector);
	
	@Query(value = "select * from song where genre = genre", nativeQuery = true)
	List<Song> findSongsByGenre(@Param("genre") String genre);
	
	@Query(value = "select * from song where language = :language", nativeQuery = true)
	List<Song> findSongsByLanguage(@Param("language") String language);

}