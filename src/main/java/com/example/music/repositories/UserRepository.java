package com.example.music.repositories;

import com.example.music.controller.login.model.response.UserResponse;
import com.example.music.dao.ArtisDAO;
import com.example.music.dao.ArtisSelect;
import com.example.music.dao.UserDAO;
import com.example.music.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Transactional
    @Modifying
    @Query(value = "update user set status = 'ShutDown' WHERE id = ?1", nativeQuery = true)
    void updateStatus(Integer id);

    @Transactional
    @Query(value = "select * from user where status = ?1 order by id desc", nativeQuery = true)
    List<User> select(String status);

    @Query(value = "select * from user where status = ?1 order by id desc", nativeQuery = true)
    Page<User> getUser(String status, Pageable pageable);

    @Query(value = "select user.id as 'id', user.name as 'name', user.avatar as 'avatar', user.account as 'email', user.gender as 'gender', user.birthday as 'birthday', " +
            "user.date_create as 'dateCreate', user.status as 'status' from user join account a on a.login = user.account " +
            "where a.role = ?1 and user.status = 'Activate' order by user.id desc limit 3", nativeQuery = true)
    List<UserDAO> getNewUserOrArtis(String role);

    @Transactional
    @Query(value = "select user.id as 'id', user.name as 'name', user.avatar as 'avatar', user.account as 'email', user.gender as 'gender', user.birthday as 'birthday', user.date_create as 'dateCreate', user.status as 'status' from user join account a on a.login = user.account where a.role = 'USER' or a.role = 'ADMIN' order by user.id desc", nativeQuery = true)
    Page<UserDAO> getAllUser(Pageable pageable);

    @Query(value = "select user.id as 'id', user.name as 'name', user.avatar as 'avatar', user.gender as 'gender', user.account as 'email', user.birthday as 'birthday', user.date_create as 'dateCreate', user.status as 'status' from user join account a on a.login = user.account where a.role = 'USER' or a.role = 'ADMIN' and user.status = ?1 order by user.id desc", nativeQuery = true)
    Page<UserDAO> getUserByStatus(String status, Pageable pageable);

    @Query(value = "select user.id as 'id', user.name as 'name', user.avatar as 'avatar', user.gender as 'gender'\n" +
            ", user.birthday as 'birthday', COALESCE(count(o.work), 0) as 'songs', COALESCE(count(f.user), 0) as 'follows', user.status as 'status' from user\n" +
            "left join follow f on user.id = f.idol\n" +
            "left join own o on user.id = o.author\n" +
            "join account a on a.login = user.account\n" +
            "where a.role = 'ARTIS'\n" +
            "group by user.id order by user.id desc ", nativeQuery = true)
    Page<ArtisDAO> getAllArtis(Pageable pageable);

    @Query(value = "select user.id as 'id', user.name as 'name', user.avatar as 'avatar', user.gender as 'gender'\n" +
            ", user.birthday as 'birthday', COALESCE(count(o.work), 0) as 'songs', COALESCE(count(f.user), 0) as 'follows', user.status as 'status' from user\n" +
            "left join follow f on user.id = f.idol\n" +
            "left join own o on user.id = o.author\n" +
            "join account a on a.login = user.account\n" +
            "where a.role = 'ARTIS' and user.status = ?1\n" +
            "group by user.id order by user.id desc ", nativeQuery = true)
    Page<ArtisDAO> getArtisByStatus(String status, Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "update user set status = ?2 where id = ?1", nativeQuery = true)
    void updateStatusUser(Integer id, String status);

    @Transactional
    @Query(value = "select user.id as 'value', user.name as 'label' from user join account a on a.login = user.account\n" +
            "where a.role = 'ARTIS' and user.status = 'Activate'", nativeQuery = true)
    List<ArtisSelect> getArtisForSelect();

    @Transactional
    @Query(value = "select user.account from user", nativeQuery = true)
    List<String> getEmailUser();

    @Transactional
    @Query(value = "select u.id, u.name, u.gender, u.birthday, u.avatar, a.role from user u left join account a on a.login = u.account\n" +
            "where u.account = ?1", nativeQuery = true)
    UserResponse getUserResponse(String login);

}
