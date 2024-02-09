package com.example.music.repositories;

import com.example.music.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Transactional
    @Modifying
    @Query(value = "update user set status = 'NGUNG_HOAT_DONG' WHERE id = ?1", nativeQuery = true)
    void updateStatus(Long id);

    @Transactional
    @Query(value = "select * from user where status = ?1 order by date_create desc, id desc", nativeQuery = true)
    List<User> select(String status);

}
