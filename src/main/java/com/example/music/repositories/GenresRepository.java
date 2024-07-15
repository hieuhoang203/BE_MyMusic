package com.example.music.repositories;

import com.example.music.dao.GenresSelect;
import com.example.music.entity.Genres;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenresRepository extends JpaRepository<Genres, Byte> {

    @Modifying
    @Transactional
    @Query(value = "update genres set status = 'ShutDown' where id = ?1", nativeQuery = true)
    void updateStatus(Byte aByte);

    @Modifying
    @Transactional
    @Query(value = "update genres set status = 'Activate' where id = ?1", nativeQuery = true)
    void returnStatus(Byte aByte);

    @Transactional
    @Query(value = "select * from genres where status = ?1 order by date_create desc, id desc", nativeQuery = true)
    List<Genres> select(String status);

    @Query(value = "select * from genres where status = ?1 order by date_create desc, id desc", nativeQuery = true)
    Page<Genres> getGenres(String status, Pageable pageable);

    @Query(value = "select * from genres order by date_create desc, id desc", nativeQuery = true)
    Page<Genres> getAll(Pageable pageable);

    @Query(value = "select id as 'value', name as 'label' from genres where status = 'Activate'", nativeQuery = true)
    List<GenresSelect> getGenresForSelect();

}
