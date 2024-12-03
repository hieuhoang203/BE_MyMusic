package com.example.music.repositories;

import com.example.music.entity.Favorite;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, String> {

    @Modifying
    @Transactional
    @Query(value = "update favorite set status = 'ShutDown' where id = ?1", nativeQuery = true)
    void updateStatus(Long aLong);

    @Transactional
    @Query(value = "select * from favorite where status = ?1 order by create_date desc, id desc", nativeQuery = true)
    List<Favorite> select(String status);

}
