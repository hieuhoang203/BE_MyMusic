package com.example.music.repositories;

import com.example.music.entity.Song;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

    @Modifying
    @Transactional
    @Query(value = "update song set status = 'ShutDown' where id = ?1", nativeQuery = true)
    void updateStatus(Long aLong);

    @Transactional
    @Query(value = "select * from song where status = ?1 order by id desc", nativeQuery = true)
    List<Song> select(String status);

    @Query(value = "select * from song where status = ?1 order by id desc", nativeQuery = true)
    Page<Song> getSong(String status, Pageable pageable);

    @Query(value = "select * from song where status = 'Activate' or status = 'ShutDown' order by id desc", nativeQuery = true)
    Page<Song> getAllSong(Pageable pageable);
    
    @Transactional
    @Modifying
    @Query(value = "update song set status = ?1 where id = ?2", nativeQuery = true)
    void updateStatus(String status, Long id);

}
