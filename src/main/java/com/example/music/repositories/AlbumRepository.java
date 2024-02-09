package com.example.music.repositories;

import com.example.music.entity.Album;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

    @Modifying
    @Transactional
    @Query(value = "update album set status = 'NGUNG_HOAT_DONG' where id = ?1", nativeQuery = true)
    void updateStatus(Long aLong);

    @Transactional
    @Query(value = "select * from album where status = ?1 order by date_create desc, id desc", nativeQuery = true)
    List<Album> select(String status);

}
