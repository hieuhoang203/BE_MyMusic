package com.example.music.repositories;

import com.example.music.dao.GenresSelect;
import com.example.music.entity.Genres;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenresRepository extends JpaRepository<Genres, String> {

    @Modifying
    @Transactional
    @Query(value = "update genres set status = ?2 where id = ?1", nativeQuery = true)
    void changeStatus(String id, String status);

    @Query(value = "select * from genres where status = ?1 order by create_date desc, id desc", nativeQuery = true)
    Page<Genres> getGenres(String status, Pageable pageable);

    @Query(value = "select * from genres order by create_date desc, id desc", nativeQuery = true)
    Page<Genres> getAll(Pageable pageable);

    @Query(value = "select code from genres", nativeQuery = true)
    List<String> getAllCode();

    @Query(value = "select name from genres", nativeQuery = true)
    List<String> getAllName();

    @Query(value = "select id as 'value', name as 'label' from genres where status = 'Activate'", nativeQuery = true)
    List<GenresSelect> getGenresForSelect();

    @Query(value = "SELECT 1 FROM tbl_genres WHERE LOWER(:column) = LOWER(:value) LIMIT 1", nativeQuery = true)
    Integer checkCodeOrNameExists(@Param("column") String column, @Param("value") String value);

}
