package com.example.music.own;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnRepository extends JpaRepository<Own, String> {

}
