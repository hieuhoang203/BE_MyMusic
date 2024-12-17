package com.example.music.repositories;

import com.example.music.dao.AlbumSelect;
import com.example.music.dao.GenresSelect;
import com.example.music.entity.Album;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, String> {

    @Modifying
    @Transactional
    @Query(value = "update album set status = 'ShutDown' where id = ?1", nativeQuery = true)
    void updateStatus(Long aLong);

    @Transactional
    @Query(value = "select * from album where status = ?1 order by create_date desc, id desc", nativeQuery = true)
    List<Album> select(String status);

    @Transactional
    @Query(value = "select * from album where status = ?1", nativeQuery = true)
    Page<Album> getAlbum(String status, Pageable pageable);

    @Transactional
    @Query(value = "select id as 'value', name as 'label' from album where status = 'Activate'", nativeQuery = true)
    List<AlbumSelect> getAlbumForSelect();

    @Transactional
    @Query(value = "select id as 'value', name as 'label' from album where status = 'Activate' and artis = ?1", nativeQuery = true)
    List<AlbumSelect> getAlbumForSelect(String artis);

}
