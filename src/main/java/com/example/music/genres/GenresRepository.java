package com.example.music.genres;

import com.example.music.comon.SelectValue;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenresRepository extends JpaRepository<Genres, String> {

    @Query(value = "SELECT 1 FROM tbl_genres WHERE LOWER(code) = LOWER(:value) LIMIT 1", nativeQuery = true)
    Integer checkCode(@Param("value") String value);

    @Query(value = "SELECT 1 FROM tbl_genres WHERE LOWER(name) = LOWER(:value) LIMIT 1", nativeQuery = true)
    Integer checkName(@Param("value") String value);

    @Modifying
    @Query(value = "update tbl_genres set status = :status where id = :id", nativeQuery = true)
    void changeStatus(@Param("id") String id, @Param("status") String status);

    @Query(value = "select * from tbl_genres where status = :status order by create_date desc, id desc", nativeQuery = true)
    Page<Genres> getGenres(@Param("status") String status, Pageable pageable);

    @Query(value = "select * from tbl_genres order by create_date desc, id desc", nativeQuery = true)
    Page<Genres> getAll(Pageable pageable);

    @Query(value = "select id as 'value', name as 'label' from tbl_genres where status = 'Activate'", nativeQuery = true)
    List<SelectValue> getGenresForSelect();

}
