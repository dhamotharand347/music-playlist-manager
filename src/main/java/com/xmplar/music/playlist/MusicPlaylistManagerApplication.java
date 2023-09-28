package com.xmplar.music.playlist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MusicPlaylistManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicPlaylistManagerApplication.class, args);
		
		System.out.println("Model success");
	}

}