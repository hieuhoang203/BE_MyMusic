package com.example.music.repositories;

import com.example.music.entity.Role;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role,  Byte> {

    @Modifying
    @Transactional
    @Query(value = "update role set status = 'NGUNG_HOAT_DONG' where id = ?1", nativeQuery = true)
    void updateStatus(Byte aByte);

    @Transactional
    @Query(value = "select * from role where status = ?1 order by date_create desc, id desc", nativeQuery = true)
    List<Role> select(String status);

}
