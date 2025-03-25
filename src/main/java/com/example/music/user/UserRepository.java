package com.example.music.user;

import com.example.music.comon.SelectValue;
import com.example.music.data.DetailAccount;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    String artisResponseQuery = "u.id as 'id', u.name as 'name', u.avatar as 'avatar', u.gender as 'gender', u.birthday as 'birthday', COALESCE(count(o.work), 0) as 'songs', COALESCE(count(f.id), 0) as 'follows', u.status as 'status'";

    String userResponseQuery = "u.id as 'id', u.name as 'name', u.avatar as 'avatar', u.login as 'email', u.gender as 'gender', u.birthday as 'birthday', u.create_date as 'dateCreate', u.status as 'status'";

    @Query(value = "select * from tbl_user where status = :status order by create_date desc", nativeQuery = true)
    List<User> selectUser(@Param("status") String status);

    @Query(value = "select * from tbl_user where status = :status order by create_date desc", nativeQuery = true)
    Page<User> getUser(@Param("status") String status, Pageable pageable);

    @Query(value = "select " + userResponseQuery + " from tbl_user u " +
            "where u.role = :role and u.status = 'Activate' order by u.create_date desc limit 3", nativeQuery = true)
    List<UserResponse> getNewUserOrArtis(@Param("role") String role);

    @Query(value = "select " + userResponseQuery + " from tbl_user u where u.role = 'USER' or u.role = 'ADMIN' order by u.create_date desc", nativeQuery = true)
    Page<UserResponse> getAllUser(Pageable pageable);

    @Query(value = "select u.id as 'id', u.name as 'name', u.avatar as 'avatar', u.gender as 'gender', u.login as 'email', u.birthday as 'birthday', u.create_date as 'dateCreate', u.status as 'status' from tbl_user u where u.role = 'USER' or u.role = 'ADMIN' and u.status = :status order by u.create_date desc", nativeQuery = true)
    Page<UserResponse> getUserByStatus(@Param("status") String status, Pageable pageable);

    @Query(value = "select " + artisResponseQuery + " from tbl_user u\n" +
            "left join tbl_follow f on u.id = f.idol\n" +
            "left join tbl_own o on u.id = o.author\n" +
            "where u.role = 'ARTIS'\n" +
            "group by u.id order by u.create_date desc ", nativeQuery = true)
    Page<ArtisResponse> getAllArtis(Pageable pageable);

    @Query(value = "select " + artisResponseQuery + " from tbl_user u\n" +
            "left join tbl_follow f on u.id = f.idol\n" +
            "left join tbl_own o on u.id = o.author\n" +
            "where u.role = 'ARTIS' and u.status = :status\n" +
            "group by u.id order by u.create_date desc ", nativeQuery = true)
    Page<ArtisResponse> getArtisByStatus(@Param("status") String status, Pageable pageable);

    @Query(value = "select " + artisResponseQuery + " from tbl_user u\n" +
            "left join tbl_follow f on u.id = f.idol\n" +
            "left join tbl_own o on u.id = o.author\n" +
            "where o.work = :work", nativeQuery = true)
    List<ArtisResponse> getArtisByOwns(@Param("work") String work);

    @Modifying
    @Query(value = "update tbl_user set status = :status where id = :id", nativeQuery = true)
    void updateStatusUser(@Param("id") String id, @Param("status") String status);

    @Query(value = "select u.id as 'value', u.name as 'label' from tbl_user u\n" +
            "where u.role = 'ARTIS' and u.status = 'Activate'", nativeQuery = true)
    List<SelectValue> getArtisForSelect();

    @Query(value = "select login from tbl_user", nativeQuery = true)
    List<String> getEmailUser();

    @Query(value = "select u.id, u.name, u.gender, u.birthday, u.avatar, u.role from tbl_user u\n" +
            "where u.login = :login", nativeQuery = true)
    DetailAccount getUserResponse(@Param("login") String login);

    @Query(value = "select 1 from tbl_user WHERE login = :email LIMIT 1 ", nativeQuery = true)
    Integer getAllEmail(@Param("email") String email);

    @Query(value = "select * from tbl_user where login = :email and status = 'Activate'", nativeQuery = true)
    User getUserByEmail(@Param("email") String email);

    Optional<User> findUserByLogin(String login);
}
