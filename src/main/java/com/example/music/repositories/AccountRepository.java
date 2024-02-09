package com.example.music.repositories;

import com.example.music.entity.Account;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    @Transactional
    @Modifying
    @Query(value = "update account set status = 'NGUNG_HOAT_DONG' where user_name = ?1", nativeQuery = true)
    void updateStatus(String userName);

    @Transactional
    @Query(value = "select * from account where status = ?1 order by date_create desc, id desc", nativeQuery = true)
    List<Account> select(String status);

}
