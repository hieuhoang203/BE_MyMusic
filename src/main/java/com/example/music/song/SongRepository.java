package com.example.music.song;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, String> {

    @Modifying
    @Query(value = "update tbl_song set status = 'ShutDown' where id = :id", nativeQuery = true)
    void updateStatus(@Param("id") String id);

    @Query(value = "select * from tbl_song where status = :status order by create_date desc", nativeQuery = true)
    List<Song> select(@Param("status") String status);

    @Query(value = "select * from tbl_song where status = :status order by create_date desc", nativeQuery = true)
    Page<Song> getSong(Pageable pageable, @Param("status") String status);

    @Query(value = "select * from tbl_song order by create_date desc", nativeQuery = true)
    Page<Song> getAllSong(Pageable pageable);

    @Query(value = "select * from tbl_song order by create_date desc", nativeQuery = true)
    List<Song> getAllSong();

}
