package com.example.music.song_favorite;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongFavoriteRepository extends JpaRepository<SongFavorite, String> {

}
