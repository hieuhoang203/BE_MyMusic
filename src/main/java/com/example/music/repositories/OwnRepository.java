package com.example.music.repositories;

import com.example.music.entity.Own;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnRepository extends JpaRepository<Own, String> {

}
