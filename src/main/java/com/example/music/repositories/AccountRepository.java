package com.example.music.repositories;

import com.example.music.entity.Account;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    @Transactional
    @Modifying
    @Query(value = "update account set status = 'ShutDown' where login = ?1", nativeQuery = true)
    void updateStatus(String userName);

    @Transactional
    @Query(value = "select * from account where status = ?1 order by date_create desc, id desc", nativeQuery = true)
    List<Account> select(String status);

    @Transactional
    @Modifying
    @Query(value = "update account set status = ?2  where login =?1", nativeQuery = true)
    void updateStatusAccount(String id, String status);

    @Transactional
    @Query(value = "select * from account where login = ?1 and status = 'Activate'", nativeQuery = true)
    Optional<Account> loginAccount(String user_name);

    @Transactional
    @Modifying
    @Query(value = "update account set pass = ?2 where login = ?1", nativeQuery = true)
    void updatePassWord(String user_name, String pass_word);

    Optional<Account> findAccountByLogin(String user_name);

    boolean existsAccountByLogin(String userName);


}
