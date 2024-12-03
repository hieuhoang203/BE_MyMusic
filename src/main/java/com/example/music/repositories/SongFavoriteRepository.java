package com.example.music.repositories;

import com.example.music.entity.SongFavorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongFavoriteRepository extends JpaRepository<SongFavorite, String> {

}
