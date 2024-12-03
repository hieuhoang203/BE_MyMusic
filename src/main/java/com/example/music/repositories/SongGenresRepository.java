package com.example.music.repositories;

import com.example.music.entity.SongGenres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongGenresRepository extends JpaRepository<SongGenres, String> {

}
