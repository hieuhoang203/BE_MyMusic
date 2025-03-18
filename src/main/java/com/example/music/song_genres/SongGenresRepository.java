package com.example.music.song_genres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongGenresRepository extends JpaRepository<SongGenres, String> {

}
