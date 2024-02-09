package com.example.music.repositories;

import com.example.music.entity.FavoritesList;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoritesListRepository extends JpaRepository<FavoritesList, Long> {

    @Modifying
    @Transactional
    @Query(value = "update favorites_list set status = 'NGUNG_HOAT_DONG' where id = ?1", nativeQuery = true)
    void updateStatus(Long aLong);

    @Transactional
    @Query(value = "select * from favorites_list where status = ?1 order by date_create desc, id desc", nativeQuery = true)
    List<FavoritesList> select(String status);

}
